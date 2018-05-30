package com.ivmai.bdpush;

/**
 * 传递事件
 */
public  class CommonEvent {
        int code;   //标志
        Object data;   //数据

        public CommonEvent(int code, Object data) {
            this.code = code;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }