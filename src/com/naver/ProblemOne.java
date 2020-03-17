package com.naver;


public class ProblemOne {


    class Task {
      private String name;
      private Task next;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Task getNext() {
            return next;
        }

        public void setNext(Task next) {
            this.next = next;
        }

        public void execute(){
            System.out.println("执行任务："+name);
        }
    }
    class DAG {

    }

}
