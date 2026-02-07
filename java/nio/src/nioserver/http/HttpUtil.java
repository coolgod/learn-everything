package nioserver.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    private static final byte[] GET    = new byte[]{'G','E','T'};
    private static final byte[] POST   = new byte[]{'P','O','S','T'};
    private static final byte[] PUT    = new byte[]{'P','U','T'};
    private static final byte[] HEAD   = new byte[]{'H','E','A','D'};
    private static final byte[] DELETE = new byte[]{'D','E','L','E','T','E'};

    private static final byte[] HOST           = new byte[]{'H','o','s','t'};
    private static final byte[] CONTENT_LENGTH = new byte[]{'C','o','n','t','e','n','t','-','L','e','n','g','t','h'};

//    POST /submit HTTP/1.1     // First line: request line
//    Host: example.com         // Second line: HTTP header lines
//    Content-Type: text/plain
//    Content-Length: 11
//
//    Hello World
    public static int parseHttpRequest(byte[] src, int start, int end, HttpHeaders httpHeaders) {
        // Parse HTTP request line
        int endOfFirstLine = findNextLineBreak(src, start, end);
        if (endOfFirstLine == -1) {
            return -1;
        }

        // Parse HTTP header lines
        int prevEndOfHeader = endOfFirstLine + 1;
        int endOfHeader = findNextLineBreak(src, prevEndOfHeader, end);
        while (endOfHeader != -1 && // found a line break
                endOfHeader != prevEndOfHeader + 1 // not empty line
        ) {
            if(matches(src, prevEndOfHeader, CONTENT_LENGTH)){
                try {
                    findContentLength(src, prevEndOfHeader, end, httpHeaders);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            prevEndOfHeader = endOfHeader + 1;
            endOfHeader = findNextLineBreak(src, prevEndOfHeader, end);
        }

        // No body
        if (endOfHeader == -1) {
            return -1;
        }

        int bodyStartIndex = endOfHeader + 1;
        int bodyEndIndex  = bodyStartIndex + httpHeaders.contentLength;

        if(bodyEndIndex <= end){
            httpHeaders.bodyStartIndex = bodyStartIndex;
            httpHeaders.bodyEndIndex   = bodyEndIndex;
            return bodyEndIndex;
        }

        return -1;
    }

    private static void findContentLength(byte[] src, int startIndex, int endIndex, HttpHeaders httpHeaders) throws UnsupportedEncodingException {
        int indexOfColon = findNext(src, startIndex, endIndex, (byte)':');

        //skip spaces after colon
        int index = indexOfColon + 1;
        while(src[index] == ' '){
            index++;
        }

        int valueStartIndex = index;
        int valueEndIndex   = index;
        boolean endOfValueFound = false;

        while(index < endIndex && !endOfValueFound){
            switch(src[index]){
                case '0' : ;
                case '1' : ;
                case '2' : ;
                case '3' : ;
                case '4' : ;
                case '5' : ;
                case '6' : ;
                case '7' : ;
                case '8' : ;
                case '9' : { index++;  break; }

                default: {
                    endOfValueFound = true;
                    valueEndIndex = index;
                }
            }
        }

        httpHeaders.contentLength = Integer.parseInt(new String(src, valueStartIndex, valueEndIndex - valueStartIndex, StandardCharsets.UTF_8));
    }

    private static boolean matches(byte[] src, int offset, byte[] value){
        for(int i = offset, j = 0; j < value.length; i++, j++){
            if(src[i] != value[j]) return false;
        }
        return true;
    }

    private static int findNext(byte[] src, int startIndex, int endIndex, byte value){
        for(int index = startIndex; index < endIndex; index++){
            if(src[index] == value) {
                return index;
            }
        }
        return -1;
    }

    private static int findNextLineBreak(byte[] src, int start, int end) {
        for (int i = start; i < end; i++) {
            if (src[i] == '\n' && src[i-1] == '\r') {
                String line = new String(src, start, i, StandardCharsets.UTF_8);
                System.out.println(line);
                return i;
            }
        }

        return -1;
    }
}
