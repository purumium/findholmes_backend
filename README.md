# 탐정 중개 플랫폼: 찾아줘 홈즈
![todoup](https://github.com/user-attachments/assets/2828e037-d3fc-454b-9be6-a93a88ffd9f9)

<br>

## 프로젝트 소개
![schedules](https://github.com/user-attachments/assets/5a2b22b3-fa09-4ca1-851c-747d10b4df38)

- 스케줄러와 게이미피케이션을 결합한 할 일 관리 서비스입니다.
- 사용자가 '작심삼일'에 그치지 않고 꾸준히 목표를 달성할 수 있도록 다양한 동기 부여와 성찰 기능을 제공합니다.

<br>

## 프로젝트 핵심 기능
- **경험치와 레벨업**
  - 할 일을 완료하거나 성찰 일기를 작성할 때마다 경험치를 적립하고, 아바타의 레벨이 상승하는 등의 동기 부여 요소를 통해 목표 달성을 더욱 흥미롭게 만듭니다.
- **캘린더를 이용한 할 일 및 성장일기 관리**
  - 캘린더를 이용하여 일정을 체계적으로 관리하고, 하루의 목표를 돌아보며 성장 일기를 작성할 수 있습니다. 이를 통해 사용자는 자신의 발전 과정을 기록하고 성찰할 수 있습니다.
- **소셜 기능을 통한 동기부여**
  - 팔로우 기능을 통해 친구의 아바타방을 구경하고, 친구의 성과를 확인하며 비교할 수 있습니다. 이는 사용자 간의 경쟁을 유도하고, 지속적인 동기 부여를 제공합니다.
- **성장 리포트**
  - 자신의 목표 달성 여부와 성장 과정을 차트화하여 시각적으로 확인할 수 있습니다. 이를 통해 사용자는 자신의 발전을 한눈에 파악하고 더욱 효율적으로 목표를 설정할 수 있습니다.

<br>

## 팀원 구성

| **선푸름(팀장)** | **김찬희** | **박요한** | **이연수** |  **최유환** |
| :------: |  :------: | :------: | :------: |:------: |
| <img src="https://github.com/user-attachments/assets/071a0e18-a8cf-4d36-9c42-9985fcf76104?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/6fd98055-7e21-432f-9948-dfe88e613c3c?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/ad4245c8-8e07-4841-8d9e-ef912f46fb91?v=4" height="130" width="130"> | <img src="https://github.com/user-attachments/assets/c0305ec8-9c6e-4bfb-a783-b194f0656ca1?v=4" height="130" width="130"> |  <img src="https://github.com/user-attachments/assets/0267ffd3-8039-4d0d-9be2-99f1668e14b1?v=4" height="130" width="130"> |

</div>

<br>

## 개발 기간
- 2024-08-05 ~ 2024-08-16

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
