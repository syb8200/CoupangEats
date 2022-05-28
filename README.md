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
   
   
>**2022.05.23.(월) - 진행상황**
   
- 진행상황
1) Login : 진행률 80%
2) Signup : 진행률 90%
3) Home : 진행률 30%
   
- 개발 도중 발생한 이슈   
: 회원가입 API 연동을 하면서, sRetrofit has not been initialized, getJwt() on a null object reference 오류 두 번 발생   
: 해결) Manifest의 application 내에 android:name을 써주고, 비밀번호 입력 시 영어/숫자/특수문자 포함해서 입력하니 오류 해결  
   
- 1차 피드백   
: 화면 공유가 되지 않아 영상으로 대체 (영상에 못 보여드린 부분은 추가적으로 설명 붙임 / UI 피드백은 아직 받지 못함)   
: 계획한 것보다는 진도가 조금 느린 상황, 생산성을 높이는 데에 집중 필요   
: API가 나오자마자 쓸 수 있도록 준비둘 것   
   
   
>**2022.05.24.(화) - 진행상황**
   
- 진행상황
1) Login : 진행률 90%
2) Signup : 진행률 90%
3) Home : 진행률 40%
   
- 개발 도중 발생한 이슈   
: RecyclerView Footer 구현에서 어려움을 겪음   
: 배너 부분을 ViewPager를 사용하여 구현하였는데, 다른 화면으로 넘어가게 되면 배너 부분에서 오류 발생   
: 해결) 각각 다른 ViewHolder를 적용함으로써 해결. 배너 부분 오류 원인은 스레드를 종료시키지 않고 다른 화면으로 넘어갔기 때문이고 이는 handler를 통해 해결   
   
   
   >**2022.05.25.(수) - 진행상황**
   
- 진행상황
1) Login : 진행률 95% (로그인 API 연동은 해놨으나, 서버 측 구조 변경으로 이후에 마무리 예정)
2) Signup : 진행률 100% (유저정보 요청 시, 첫 시도 때 무조건 중복처리 되는 부분 해결)
3) Home : 진행률 45%
   
- 개발 도중 발생한 이슈   
: Signup - 유저정보 요청 시, 첫 시도 때 무조건 중복처리 되는 문제 발생   
: Home - Floating 버튼 구현 시 스크롤 때마다 사라짐/보여짐 처리에서 어려움을 겪음   
: 해결) Signup - 서버에서 요청하여 받은 데이터를 담는 변수의 자료형 변경(Boolean? = null) / Home - addOnScrollListener 이용하여 해결   
   
- 서버측과 이견 발생 및 해결   
: 문제 발견) 로그인 API 연동하여 jwt를 확인하는 과정에서, 기존에 회원가입 시 발급받은 jwt 값과 유저정보 요청 시 받은 jwt 값이 다름을 발견   
: 서버 측 의견) 기존 서비스 대로면 jwt를 일정 기간동안 갱신 시키는 방식이 맞음, 이 부분을 적용함으로써 보안성 높임, 추가로 refresh jwt도 사용 예정   
: 클라 측 의견) 2주라는 짧은 기간 내에 실제 서비스가 아닌 클론 코딩을 하는 것이기에 jwt 고정 희망, refresh jwt 개념 숙지가 완벽히 되지 않았기에 구현하는 데에 있어서 부족함이 있다고 판단   
: 개발 리더 조언) 클라이언트가 힘들다고 판단했다면 우선순위를 뒤로 미뤄서 처리하는 것이 적절, 2주 동안 작업하는 과정에서 refresh jwt 부분은 채점 기준에 속하지 않음, 실제 서비스를 작업하는 것이 아닌 클론 코딩이기에 시간이 많지 않은 상황에서 차선책을 생각해서 진행하는 것이 적절.   
: 결과) 서버 측과 클라이언트 협의 완료. 서버 측에서 DB 저장 구조 수정 예정.   
   
   
   >**2022.05.26.(목) - 진행상황**
   
- 진행상황
1) Login : 진행률 100%
3) Home : 진행률 65%
   
- 개발 도중 발생한 이슈   
: Home - 골라먹는 맛집 옵션 부분이 상단이 상단 툴바 및 상단 카테고리 뷰에 가려지지 않고 붙는 부분에 대해서 어려움을 겪음   
: Home - 상단 카테고리 뷰도 마찬가지로 스크롤에 따라 보여짐/사라짐 구현의 어려움을 겪음   
: 해결) setOnScrollChangeListener 사용, 좌표 값에 따라 변하도록 구현   
    
- 클라이언트-서버 협의 및 결정 사항   
: 쿠팡이츠 중요도 순서에 따르면 '홈 -> 음식점 정보 -> 주문' 순이기에 주요 기능들을 우선적으로 처리하기로 함. 때문에 홈 화면의 '골라먹는 맛집'의 옵션들 구현은 후순위로 미루기로 함   
: 홈의 '골라먹는 맛집' 옵션들의 경우, 모든 옵션들을 구현하기에는 시간적 여유가 없을 뿐더러, 일부 옵션 구현에 제한이 있음. 때문에 디폴트로 별점순, 가까운순, 신규매장 순으로 조회하는 기능만 구현한 후, 배달비, 최소주문은 이후에 추가적으로 구현하기로 함   
: 홈의 '골라먹는 맛집' 리스트의 경우, 처음 회의 떄에 무한스크롤로 판단하여 15개씩 3페이지씩 나눠서 받기로 결정하였으나, 다시 확인하는 과정에서 무한스크롤로 보기 어렵다고 판단하여 리스트 데이터를 한 번에 받아오는 것으로 협의   
: 위의 변경 사항들에 대해서 개발 리더들의 확인 필요. (피드백 기다리는 중)   
   
   
   >**2022.05.27.(금) - 진행상황**
   
- 진행상황   
1) Home : 진행률 75%
2) LocationSetting : 진행률 15%
   
- 개발 도중 발생한 이슈   
: Home - 카테고리 API를 연동하는 중에, 대량의 데이터를 받아오기 위해 배열을 사용할 때 어려움을 겪음   
: 해결) 배열 사용 시, 배열 사이즈를 지정해줌으로써 해결 -> var 리스트이름 = MutableList(사이즈,{""})   
   
    
   >**2022.05.28.(토) - 진행상황**
   
- 진행상황   
1) Home : 진행률 75%
2) LocationSetting : 진행률 15%
3) Store : 진행률 30%
   
- 개발 도중 발생한 이슈   
: Store - 레이아웃을 전체적으로 구성하는데에 어려움을 겪음 
: 해결) AppbarLayout + CollapsingToolbarLayout    
   
    
