package com.mayak.chuckchuck.oauth2.user;

import com.mayak.chuckchuck.oauth2.exception.OAuth2AuthenticationProcessingException;
import com.mayak.chuckchuck.oauth2.user.Google.GoogleOAuth2UserInfo;
import com.mayak.chuckchuck.oauth2.user.Kakao.KakaoOAuth2UserInfo;
import com.mayak.chuckchuck.oauth2.user.Naver.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   String accessToken,
                                                   Map<String, Object> attributes) {
        if (OAuth2Provider.GOOGLE.getRegistrationId().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(accessToken, attributes);
        } else if (OAuth2Provider.NAVER.getRegistrationId().equals(registrationId)) {
            return new NaverOAuth2UserInfo(accessToken, attributes);
        } else if (OAuth2Provider.KAKAO.getRegistrationId().equals(registrationId)) {
            return new KakaoOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}
