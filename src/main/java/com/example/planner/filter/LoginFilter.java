package com.example.planner.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/users", "/sign/login", "/sign/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        // whiteList에 포함된 경우 true -> !true -> false
        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false); //httpRequest에서 세션을 꺼내는 법 디버깅활용, filter


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

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

}
