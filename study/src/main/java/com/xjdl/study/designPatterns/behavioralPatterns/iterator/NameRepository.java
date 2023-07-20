package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameRepository implements Container {
    public String[] names = {"a", "b", "c", "d"};

    @Override
    public Iterator getIterator() {
        return new NameIterator();
    }

    private class NameIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < names.length;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return names[index++];
            }
            return null;
        }
    }
}
