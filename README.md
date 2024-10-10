# 탐정 중개 플랫폼 '찾아줘 홈즈'
![image](https://github.com/user-attachments/assets/e9a989a9-8d31-458e-b576-5fa3b241bc4d)

<br>

## 프로젝트 소개
![image](https://github.com/user-attachments/assets/eb3dabc7-e2e0-421b-815e-249fb655c9bb)

- 고객의 요구에 맞춰 전문 탐정을 매칭해 주는 탐정 중개 플랫폼
- 고객은 요구사항에 맞는 전문 탐정을 빠르고 쉽게 찾고, 탐정은 더 많은 사건을 경험하며 전문성을 높일 수 있음
- 검증된 탐정과의 신뢰성 있는 상담과 합리적인 가격 비교 서비스를 제공

<br>

## 프로젝트 핵심 기능
- **검증된 탐정만 소개**
  - 탐정 등록증과 사업자 등록증을 소지한 탐정만이 등록 가능하여, 신뢰할 수 있는 서비스 이용 가능
- **고객 맞춤형으로 전문 분야의 탐정 매칭**
  - 탐정을 사람찾기, 사이버수사 등 전문 분야별로 구분
  - 고객의 요구사항과 일치하는 전문 분야의 탐정을 매칭
- **가격 비교 기능**
  - 여러 명의 탐정이 제안한 가격을 쉽게 비교하고, 합리적인 가격으로 탐정 서비스 이용 가능
- **포인트 결제 기능**
  - 고객이 탐정과 원활한 채팅하기 위하여 포인트 결제(5회 채팅까지는 무료)
  - 탐정이 고객에게 답변서(견적서)를 보내기 위해 포인트 결제
- **실시간 채팅 기능**
  - 고객과 탐정이 실시간 소통을 위한 채팅 기능 제공

<br>

## 팀원 구성

| **선푸름(팀장)** | **조성현** | **이혜연** | **최유환** |
| :------: |  :------: | :------: | :------: |
| ![image](https://github.com/user-attachments/assets/b9add712-b351-4daa-b72b-611e2b372ed7)|
![image](https://github.com/user-attachments/assets/dbb198d2-8747-4dd8-aea5-d463d0dff50f)|


<img src="https://github.com/user-attachments/assets/dcb77afc-1423-4cd3-bb05-88bb94186b7b
?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/6fd98055-7e21-432f-9948-dfe88e613c3c?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/ad4245c8-8e07-4841-8d9e-ef912f46fb91?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/c0305ec8-9c6e-4bfb-a783-b194f0656ca1?v=4" height="130" width="130"> | 

</div>

<br>

## 개발 기간
- 2024-08-26 ~ 2024-10-07

<br>

## 개발 환경

<span><strong>프론트엔드 (Frontend)</strong></span>
- <span>
  <img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vuedotjs&logoColor=white">
  <img src="https://img.shields.io/badge/FullCalendar-2C2255?style=for-the-badge&logo=fullcalendar&logoColor=white">
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/>
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
</span>

<span><strong>백엔드 (Backend)</strong></span>
- <span>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MyBatis-BF0024?style=for-the-badge&logo=mybatis&logoColor=white"/>
</span>

<span><strong>개발 도구 (IDE)</strong></span>
- <span>
  <img src="https://img.shields.io/badge/IntelliJ IDEA-2C2255?style=for-the-badge&logo=intellij-idea&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
</span>

<span><strong>서버</strong></span>
- <span>
  <img src="https://img.shields.io/badge/Tomcat-F8DC75?style=for-the-badge&logo=apache-tomcat&logoColor=black"/>
</span>

<span><strong>데이터베이스</strong></span>
- <span>
  <img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white"/>
</span>

<span><strong>버전 관리 및 협업 도구</strong></span>
- <span>
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"/>
  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white"/>
</span>

<br>


## 역할 분담

### 🌱 최유환
- **회원관리**
  - 회원 가입 시 아이디 중복 검사, 로그인, 로그아웃
  - 데이터베이스 테이블 및 트리거 생성
  - 사용자 정보를 vuex로 관리
  - 오늘의 할 일 체크리스트를 캘린더와 연동하여 즉시 반영
<br>

### 🌱 이연수
- **할일 캘린더**
  - 할 일 추가, 수정 및 삭제, 날짜별 할 일을 캘린더와 연동하여 관리
  - 할 일 별로 달성 및 미달성 여부를 체크
  - 할 일 달성하면 경험치가 적립되고, 프로필 상단에 알림 표시
  - 전체 알림창(alert) 기능 관리
  - 전체 CSS 스타일링
<br>

### 🌱 선푸름
- **성장일기 캘린더**
  - 성장일기 작성 시 캘린더와 연동, 성장일기 작성, 수정 및 삭제
  - 사진 업로드 및 미리보기 기능을 제공
  - 일기 작성 시 사용자가 체크한 감정이 캘린더의 날짜 셀에 이미지로 표시
  - 경험치 적립으로 레벨이 업되면 아바타 이미지가 업데이트(vuex로 통합 관리)
  - 전체 CSS 스타일링, 로고, 아바타 이미지 및 아이콘 제작
<br>

### 🌱 김찬희
- **팔로우**
  - 팔로잉 & 언팔로우, 팔로워, 사용자 검색 기능
  - 사용자 이미지 클릭시 사용자 아바타 방으로 이동
  - 팔로우 모달창
  - 전체 사용자 정보를 vuex로 관리 
<br>

### 🌱 박요환
- **아바타 및 리포트**
  - 나의 아바타방(레벨, 나의 레벨에 맞는 아바타, 경험치를 제공, 방명록 기능)
  - 친구의 아바타방(방명록 작성, 수정 및 삭제)
  - 나의 아바타와 친구의 아바타 이미지, 레벨, 경험치 비교
  - 사용자의 활동 내역(월별 할일 달성률 등)을 차트화하여 리포트 제공
<br>
