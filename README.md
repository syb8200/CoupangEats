# 🏕 컴공선배 Rising Test 🏕
   
▶︎ 기간: 2022년 05월 21일 ~ 2022년 06월 03일  
   
▶︎ 종류: 쿠팡이츠(B)  
   
▶︎ 사용 언어: Kotlin(코틀린)  
   
---
  
   
>**2022.05.21.(토) - 진행상황**
- 기획서 변동사항   
: 쿠팡에서 소셜 로그인과 관련되서 Open API를 제공하지 않아 구현하지 않는 것으로 결정
   
- 진행상황
1) 기획서 작성 및 제출 : 진행률 100%
2) Splash : 진행률 100%
3) BottomNav : 진행률 100%
4) Login : 진행률 50%
5) Signup : 진행률 0%
   
- 개발 도중 발생한 이슈   
: Login에서 TextInputLayout 밒 TextInputEditText를 사용하면서 어려움이 있었음   
: TextInputLayout을 커스텀 하는 중에 변경사항이 반영이 안되는 이슈 발생   
: 해결) 이 부분에 대해서 style과 theme을 동시에 사용함으로써 해결   
   
   
>**2022.05.22.(일) - 진행상황**
   
- 진행상황
1) Login : 진행률 80%
2) Signup : 진행률 80%
   
- 개발 도중 발생한 이슈   
: Signup의 EditText를 제어하는 부분에서 어려움을 겪음   
: 앞 또는 중간에 문자(열)를 포함해서 체크할 때 어려움을 겪음   
: 해결) 문자(열) 포함 관련 이슈는 contains와 startsWith를 사용함으로써 해결   
   
- 위클리스크럼    
<진행상황>   
: 도나 - 기획서 변동 사항(스크린샷 추가) / ERD 설계(50%) / 환경구축(100%)   
: 코어 - ERD 테이블 추가 / ec2 인스턴스 생성(100%) / rds 구축(95%) / 스프링부트 ssl적용완료   
: 노바 - Splash(100%) / BottomNav(100%) / Signup (80%) / Login(80%)   
<목표>
: 도나 - 회원가입/로그인 API 구현 및 ERD 설계 완료   
: 코어 - dev/prod 서버 구축 완료 (아침 9시까지)   
: 노바 - 홈 Fragment UI 완성 및 회원가입/로그인 API 연동   
   
