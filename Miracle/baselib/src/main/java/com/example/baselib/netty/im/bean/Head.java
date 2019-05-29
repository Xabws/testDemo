package com.example.baselib.netty.im.bean;

public class Head {
        private String msgId;
        private int msgType;
        private int msgContentType;
        private long timestamp;
        private int statusReport;
        private String extend;

    public Head() {
    }

    public Head(String msgId, int msgType, int msgContentType, String fromId, String toId, long timestamp, int statusReport, String extend) {
            this.msgId = msgId;
            this.msgType = msgType;
            this.msgContentType = msgContentType;
            this.timestamp = timestamp;
            this.statusReport = statusReport;
            this.extend = extend;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public int getMsgContentType() {
            return msgContentType;
        }

        public void setMsgContentType(int msgContentType) {
            this.msgContentType = msgContentType;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatusReport() {
            return statusReport;
        }

        public void setStatusReport(int statusReport) {
            this.statusReport = statusReport;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }

        @Override
        public String toString() {
            return "Head{" +
                    "msgId='" + msgId + '\'' +
                    ", msgType=" + msgType +
                    ", msgContentType=" + msgContentType +
                    ", timestamp=" + timestamp +
                    ", statusReport=" + statusReport +
                    ", extend='" + extend + '\'' +
                    '}';
        }
    }
