package com.prix.homepage.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * uri를 뷰 템플릿에 currentUri 라는 변수명으로 전달하는 Interceptor
 */
@Component
public class UriInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 현재 요청의 URI를 가져와 모델에 추가 : 공통 header에서 uri에 따른 navbar 설정 용도
        String uri = request.getRequestURI();
        request.setAttribute("currentUri", uri);
        
        return true;
    }

}
