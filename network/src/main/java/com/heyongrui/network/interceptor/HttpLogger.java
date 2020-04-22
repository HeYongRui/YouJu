package com.heyongrui.network.interceptor;

import android.util.Log;

/**
 * Created by lambert on 2017/7/28.
 * Json格式化日志拦截器
 */

public class HttpLogger implements HttpLogInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
//        longLogPrint("HttpLogger", message, 3);
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0);
        }
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = formatJson(decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        if (message.startsWith("<-- END HTTP")) {
            longLogPrint("HttpLogger", mMessage.toString(), 3);
        }
    }

    /**
     * 长日志截断处理
     *
     * @param tag   标识
     * @param msg   打印数据
     * @param level 打印的日志等级(1=Verbose 2=Debug 3=Info 4=Warn 5=Error)
     */
    private void longLogPrint(String tag, String msg, int level) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0) {
            return;
        }

        int segmentSize = 3 * 1024;
        long length = msg.length();
        // 长度小于等于限制直接打印
        if (length <= segmentSize) {
            if (level == 1) {
                Log.v(tag, msg);
            } else if (level == 2) {
                Log.d(tag, msg);
            } else if (level == 3) {
                Log.i(tag, msg);
            } else if (level == 4) {
                Log.w(tag, msg);
            } else if (level == 5) {
                Log.e(tag, msg);
            }
        } else {
            // 循环分段打印日志
            while (msg.length() > segmentSize) {
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                if (level == 1) {
                    Log.v(tag, logContent);
                } else if (level == 2) {
                    Log.d(tag, logContent);
                } else if (level == 3) {
                    Log.i(tag, logContent);
                } else if (level == 4) {
                    Log.w(tag, logContent);
                } else if (level == 5) {
                    Log.e(tag, logContent);
                }
            }
            // 打印剩余日志
            if (level == 1) {
                Log.v(tag, msg);
            } else if (level == 2) {
                Log.d(tag, msg);
            } else if (level == 3) {
                Log.i(tag, msg);
            } else if (level == 4) {
                Log.w(tag, msg);
            } else if (level == 5) {
                Log.e(tag, msg);
            }
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加空格
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * 解码Unicode
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}