# boardproject
Java + Spring Boot + PostgreSQL

## 주요 기술 스택
- Java 21
- Spring Boot 3.1.4
- PostgreSQL

## 주요 기능
- 게시판 CRUD REST API 구현: 게시물에 대한 작성, 수정, 삭제 등의 기본적인 CRUD 기능을 제공합니다.
- Spring Security를 활용한 회원 가입: Spring Security를 이용하여 안전하게 회원 가입을 구현했습니다.
- JWT 토큰을 활용한 로그인: 로그인 시에는 Jwt 토큰을 발급받아 보안을 강화하였습니다.

## 프로젝트 실행 방법
1. 소스 코드를 클론합니다.
```
git init
git remote add origin https://github.com/KathleenJung/boardproject.git
git clone
```
2. PostgreSQL 데이터베이스 정보와 JWT 관련 정보를 application.properties 파일에 입력합니다.
4. IDE 또는 터미널에서 프로젝트를 빌드하고 실행합니다.
```
./mvnw clean install
./mvnw spring-boot:run
```
프로젝트는 http://localhost 에서 실행됩니다.

## API 호출 예시
### USER API
#### 회원 가입
![image](https://github.com/KathleenJung/boardproject/assets/85939045/0eae3d22-3028-4fb7-ac7b-fb4b447f923a)
#### 로그인 - JWT 토큰 발급
![image](https://github.com/KathleenJung/boardproject/assets/85939045/bd75b817-d238-4954-9aee-d1612d0c67ae)
#### 관리자 페이지 접속
- 관리자 토큰으로 접속 시
![image](https://github.com/KathleenJung/boardproject/assets/85939045/ee439630-73e8-4591-ae8d-7cd19a626e48)
- 일반 회원 토큰으로 접속 시
![image](https://github.com/KathleenJung/boardproject/assets/85939045/dc9306d1-74a5-448e-881a-e8fc1969ebcb)
#### 회원 정보 조회
- 토큰으로 자신의 회원 정보 조회 (Password는 null로 처리)
![image](https://github.com/KathleenJung/boardproject/assets/85939045/dac168cb-c492-4fd6-891a-65ddc75b9e59)
- 운영자 전용 타인 회원 정보 조회
![image](https://github.com/KathleenJung/boardproject/assets/85939045/e22c1f2d-b782-408c-9ef7-c6bae9205704)
- 운영자 전용 타인 회원 정보 조회를 일반 회원이 호출한 경우
![image](https://github.com/KathleenJung/boardproject/assets/85939045/82417979-567b-4711-ac40-32100bf84f25)
### BOARD API
#### 전체 게시물 목록 조회
![image](https://github.com/KathleenJung/boardproject/assets/85939045/0a7d0d16-0229-4f1f-9f88-2d2d8e43dfb8)
#### 이외에 게시물 조회/추가/수정/삭제 가능









_References_  
[스프링부트 3.0이상 Spring Security 기본 세팅 (스프링 시큐리티)](https://nahwasa.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-30%EC%9D%B4%EC%83%81-Spring-Security-%EA%B8%B0%EB%B3%B8-%EC%84%B8%ED%8C%85-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0#6._DB%EC%97%90_%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8%EA%B0%80_%EA%B7%B8%EB%8C%80%EB%A1%9C_%EB%B3%B4%EC%9D%B4%EB%8A%94%EB%8D%B0_%EC%9D%B4%EA%B1%B0_%EA%B4%9C%EC%B0%AE%EB%82%98%EC%9A%94?!)  
[Spring Security + JWT 을 구현해보자!](https://velog.io/@limsubin/Spring-Security-JWT-%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%B4%EB%B3%B4%EC%9E%90#-span-stylecolorlightcoraldatasql-span)
