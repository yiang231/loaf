function tableInit(){
	var t = document.getElementById("tablesorter").getElementsByTagName("tr");
	for(var i=1;i<t.length;i++){
		t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?"#f9f9f9":"#fff";
		t[i].onclick=function(){
			if(this.x!="1"){
				for(var j=1;j<t.length;j++){
					t[j].style.backgroundColor=(t[j].sectionRowIndex%2==0)?"#f9f9f9":"#fff";
					t[j].x="0";
				}
				this.x="1";
				this.style.backgroundColor="#f0f0f0";
			}
		};
		t[i].onmouseover=function(){
			if(this.x!="1"){
				this.style.backgroundColor="#f0f0f0";
			}
		};
		t[i].onmouseout=function(){
			if(this.x!="1"){
				this.style.backgroundColor=(this.sectionRowIndex%2==0)?"#f9f9f9":"#fff";
			}
		}
	}
}