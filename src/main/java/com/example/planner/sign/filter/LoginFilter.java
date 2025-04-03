package com.example.planner.sign.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 화이트 리스트에 등록된 url list
    private static final String[] WHITE_LIST = {"/", "/users", "/sign/login", "/sign/logout"};

    /**
     * 요청을 처리하기 전에 로그인이 됐는지 안 됐는지 여부 체크
     * @param request 서블릿 요청
     * @param response 서블릿 응답
     * @param filterChain 필터 체인
     * @throws IOException I/O 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // HttpServletRequest로 변환하여 요청 URL을 가져옴
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        // HttpServletResponse로 변환하여 응답을 설정할 수 있도록 함
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        // whiteList에 포함된 경우 true -> !true -> false
        if (!isWhiteList(requestURI)) {

            // 현재 세션을 가져오되, 세션이 없으면 null 반환
            HttpSession session = httpRequest.getSession(false);

            // 세션이 없거나 sessionKey가 없으면 로그인되지 않은 상태로 간주
            if(session == null || session.getAttribute("sessionKey") == null) {
                // HttpServletResponse에서 400번대 클라이언트 에러 발생시킴 **원인
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }



            // 로그인 성공 로직
            log.info("로그인 성공!!");

        }

        // 1번 경우: whiteList에 등록된 uri 요청이면 chain.dofilter 호출
        // 2번째 경우 whiteList가 아닌 경우 위에 있는 필터 로직을 통과하고 chain.dofilter로 다음 필터나 servlet 호출
        filterChain.doFilter(request, response);

    }

    /**
     * 요청 url가 화이트 리스트에 있는지 체크
     * @param requestURI 요청 uri
     * @return 화이트 리스트에 포함된 uri면 true 없으면 false
     */
    private boolean isWhiteList(String requestURI) {
        // 화이트 리스트에 포함된 uri인지 확인
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

}
