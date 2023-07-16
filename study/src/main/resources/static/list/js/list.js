/*
这段代码创建了一个 Vue 实例，主要包含以下内容：

1. 在 HTML 中找到 id 为 "userList" 的元素，并将其设为 Vue 实例的挂载点。
2. 创建了一个 data 对象，在其中定义了一个 userList 数组。
3. 定义了两个异步函数 `findAll()` 和 `icoCreate()`，一个用于获取用户列表数据，另一个用于设置 favicon。
4. 在 `findAll()` 中，使用 Axios 库发送 GET 请求到本地服务器的 API 地址 "http://localhost:8080/mpuser/selectAll"，并使用 async/await 解决异步请求的问题。将响应数据中 result 字段的值赋给 userList 数组。
5. 在 `icoCreate()` 中，使用 document.querySelector() 方法查找 head 标签中带有 rel 属性的 link 元素，如果找不到则创建一个新的 link 元素。然后使用 Axios 库发送 GET 请求到 GitHub API interface 获取当前开发者的头像 url，并将其设为 favicon 的 href 属性值。如果请求失败，将 link 的 href 设置为默认的 favicon 地址。
6. 在 created 生命周期函数中，将 `findAll()` 和 `icoCreate()` 函数设置为 await，即在获取用户列表数据和设置 favicon 图标时使用 await 关键字，以便避免异步请求的顺序问题。如果有错误发生，则在控制台中输出错误信息。
*/
const baseUrl = 'http://localhost:8080';
new Vue({
    el: "#userList",
    data: {
        searchText: '',
        userList: []    //用户列表
    },
    methods: {
        async findAll() {
            try {
                const response = await axios.get("/mpuser/selectAll", {
                    timeout: 5000
                    , baseURL: baseUrl
                }); // 设置请求超时时间为 5 秒
                if (response.status !== 200) {
                    throw new Error(response.statusText);
                }
                this.userList = response.data.result;
            } catch (error) {
                console.error('Failed to get userList', error);
                this.userList = null;
            }
        }
        , async icoCreate() {
            const existingLink = document.querySelector("link[rel*='icon']");
            const link = existingLink || document.createElement('link');
            link.type = 'image/x-icon';
            link.rel = 'shortcut icon';
            const githubApi = 'https://api.github.com/users/yiang231';
            try {
                const response = await axios.get(githubApi, {timeout: 5000}); // 设置请求超时时间为 5 秒
                link.href = response.data.avatar_url;
            } catch (error) {
                console.log(error);
                link.href = 'https://example.com/favicon.ico'; // 替换为默认的 favicon 地址
            }
            document.head.appendChild(link); // 将标签添加到头部
        }
        , async handleInput() {
            try {
                const res = await axios.get('/mpuser/query/' + this.searchText, {
                        baseURL: baseUrl
                    }
                )
                this.userList = res.data
            } catch (error) {
                console.error(error)
            }
        }
    },
    async created() { // 将 created 生命周期函数设为异步函数
        try {
            await this.findAll();
            await this.icoCreate();
        } catch (error) {
            console.error('Failed to init Vue.', error);
        }
    }
});
