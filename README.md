<p align="center">
<img width="400" alt="스크린샷 2022-12-19 오후 5 04 49" src="https://user-images.githubusercontent.com/54930365/208376800-81b697d2-d459-47f8-bcc2-bd29bef28500.png">
</p> 

<div align="center">  

## 🎁 특별한 가격을 선물하는 식품 특가 정보 서비스 SouP 입니다. 🎁
_🏆 카카오 클라우드 스쿨 1기 개발자 과정 최종 프로젝트 우수상 🏆_  
_📆 개발 기간: 2022.11.01 ~ 2022.12.12 📆_  
</div> 
<br><br>  

## 📍 서비스 소개
- 카카오 쇼핑, 11번가, 홈플러스 등 다양한 쇼핑몰의 식품 특가 정보를 __30분 주기__ 로 업데이트하여 한눈에👀 보여주는 __클라우드 네이티브 웹 서비스__ 입니다.  
- 다양한 쇼핑몰에서 빠르게 변동하는 식품의 특가 정보를 한눈에 확인하기 어렵다는 기존의 문제점을 해소합니다.  
- 사용자가 편리하게 식품 특가 정보를 확인하고 쇼핑할 수 있도록! 로그 기반의 상품 추천, 봇을 통한 사용자 친화적인 검색, 획일화된 카테고리 등 다양한 기능들을 제공합니다.  
<br> 


## 📍 기능
<div align="center">  

<img width="700" alt="스크린샷 2022-12-23 오전 1 08 23" src="https://user-images.githubusercontent.com/54930365/209175155-e763815e-7267-4893-96ab-35356601686c.png">

</div> 
<br>

## 📍 주요 기능
<div align="center">  
<img width="700" alt="스크린샷 2022-12-22 오후 6 40 28" src="https://user-images.githubusercontent.com/54930365/209105262-494aaf62-9eab-446a-adb2-e231489178ec.png">  

<br><br>   

<img width="500" alt="추천 기능" src="https://user-images.githubusercontent.com/54930365/209110644-175645f9-3bea-42ab-baeb-2995cab54e54.gif">   

__추천 기능 💁__  
<br> 

<img width="500" alt="검색 기능" src="https://user-images.githubusercontent.com/54930365/209109015-25770275-9b04-44e1-9bbc-804167c4be23.gif">  

__검색 기능 🔍__  
<br> 

<img width="500" alt="봇 기능" src="https://user-images.githubusercontent.com/54930365/209153651-3bfe1caa-3aa3-49cf-ba29-7b4265278967.gif">   

__봇 기능 🤖__  

</div> 
<br>

## 📍 시연 영상

[🔗 SouP 시연 영상](https://youtu.be/wgamyOjmULU)
<br>

## 📍 멀티 모듈 기반의 백엔드 서비스 분리
<div align="center">  
<img width="730" alt="스크린샷 2022-12-23 오전 1 23 35" src="https://user-images.githubusercontent.com/54930365/209178111-ab449933-34cc-43a6-b5b1-04a050c1dee3.png">
</div> <br>  

> 연동되는 데이터베이스 종류, 주요 기능을 고려하여 백엔드를 __회원 서비스, 관리자 서비스, 검색 & 봇 서비스 총 3개의 서비스로 분리했습니다.__    
> 서비스 분리를 통해 각 서비스는 독립적으로 실행되고 각각 명확하고 세분화된 역할을 갖습니다. 
> 세분화된 서비스 덕분에, 디버깅, 로깅 등의 모니터링과 유지보수가 용이해지고 각 서비스 별로 자유로운 스케일 아웃이 가능합니다. 또한 다른 서비스의 장애로 부터 영향을 받지 않습니다.   
> 쿠버네티스 환경에서는 DNS 기능과 로드밸런싱 기능을 내장하고 있기 때문에 Service Mesh, API Gateway 또는 spring cloud를 사용하지 않고 __모듈을 분리하는 방식__ 으로 서비스를 분리했습니다. 이때, 각 모듈이 spring security, error handler 등의 공통 기능을 수행하는 공통 모듈을 하위 모듈로 참조하도록 함으로써 서비스 간의 코드 중복을 최소화했습니다.

<br>

## 📍 AWS 설계도
<div align="center">  
<img width="700" alt="스크린샷 2022-12-22 오후 6 35 08" src="https://user-images.githubusercontent.com/54930365/209104309-ff2d368e-3418-451d-9b19-dbc68b6eea33.png">  
 
 __전체 아키텍처 설계도__  
<br> <br>  


<img width="700" alt="스크린샷 2022-12-22 오후 6 36 03" src="https://user-images.githubusercontent.com/54930365/209104477-aa7fb1b6-40f4-450d-a1c2-29eeb9e05da2.png">  
 
__EKS 아키텍처 설계도__  

</div> 
<br>

## 📍 주요 기술 스택
<div align="center">  
<img width="700" alt="스크린샷 2022-12-22 오후 6 32 07" src="https://user-images.githubusercontent.com/54930365/209103746-d557229e-4e2f-45d0-9864-ca22542b33ac.png">  
<br> <br>  

|분야|주요 기술 스택|
|:---:|:---:|
|__DATA PLATFORM__|<img src="https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white"> <img src="https://img.shields.io/badge/KAFKA-231F20?style=flat-square&logo=Apache Kafka&logoColor=white"> |
|__FRONTEND__|<img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=flat-square&logo=Javascript&logoColor=black"> <img src="https://img.shields.io/badge/REACT-61DAFB?style=flat-square&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/NGINX-009639?style=flat-square&logo=nginx&logoColor=black"> |
|__BACKEND__|<img src="https://img.shields.io/badge/SPRING BOOT-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/GRADLE-02303A?style=flat-square&logo=Gradle&logoColor=white"/> <img src="https://img.shields.io/badge/MYSQL-4479A1?style=flat-square&logo=Mysql&logoColor=white"/> <img src="https://img.shields.io/badge/REDIS-DC382D?style=flat-square&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/ELASTICSEARCH-005571?style=flat-square&logo=Elasticsearch&logoColor=white"/>|
|__CLOUD__|<img src="https://img.shields.io/badge/DOCKER-2496ED?style=flat-square&logo=Docker&logoColor=white"> <img src="https://img.shields.io/badge/KUBERNETES-326CE5?flat-square&logo=kubernetes&logoColor=white"> <img src="https://img.shields.io/badge/AMAZONAWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/TERRAFORM-7B42BC?flat-square&logo=terraform&logoColor=white"> |
|__LOGGING__|<img src="https://img.shields.io/badge/ELASTICSEARCH-005571?style=flat-square&logo=Elasticsearch&logoColor=white"/> <img src="https://img.shields.io/badge/FLUENTD-0E83C8?style=flat-square&logo=Fluentd&logoColor=white"/> <img src="https://img.shields.io/badge/KIBANA-005571?style=flat-square&logo=Kibana&logoColor=white"/>|
|__CI/CD__|<img src="https://img.shields.io/badge/GITOPS-F05032?style=flat-square&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/ARGOCD-EF7B4D?style=flat-square&logo=Argo&logoColor=white"/>|
|__MONITORING__|<img src="https://img.shields.io/badge/PROMETHEUS-E6522C?style=flat-square&logo=Prometheus&logoColor=white"/> <img src="https://img.shields.io/badge/GRAFANA-F46800?style=flat-square&logo=Grafana&logoColor=white"/> |
</div> 
<br> 

## 📍 DevSevOps
<div align="center">  
<img width="710" alt="스크린샷 2022-12-23 오전 1 51 58" src="https://user-images.githubusercontent.com/54930365/209186111-92535195-a489-4dca-9581-e76812f3b659.png">
</div> 
<br> 

## 📍 협업 방식
<div align="center">  
<img width="700" alt="스크린샷 2022-12-22 오후 7 17 03" src="https://user-images.githubusercontent.com/54930365/209112317-9b01340e-3a9a-4465-98b5-81e694a0f2ac.png">
</div> 
<br>

## 📍 레포지토리 
<div align="center">  

|레포지토리 |링크|설명|
|:---:|:---:|:---:|
|SpecialPriceInformationCrawler|[링크](https://github.com/kakao-shop/SpecialPriceInformationCrawler)|상품 크롤링 코드|
|CategoriesML|[링크](https://github.com/kakao-shop/CategoriesML)|카테고리 분류 코드|
|soup-frontend|[링크](https://github.com/kakao-shop/soup-frontend)|프론트엔드 코드|
|soup-backend|[링크](https://github.com/kakao-shop/soup-backend)|백엔드 코드|
|soup-docker|[링크](https://github.com/kakao-shop/soup-docker) |docker-compose 코드|
|manifest|[링크](https://github.com/kakao-shop/manifest)|kubernetes manifest files| 
 </div> 
<br>

## 📍 팀원 소개
<div align="center">  
<img width="1000" alt="스크린샷 2022-12-22 오후 7 15 20" src="https://user-images.githubusercontent.com/54930365/209111982-0de47974-bd1b-4136-97f5-ed827102a2da.png">
</div> 

