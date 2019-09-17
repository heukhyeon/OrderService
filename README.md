OrderService
===============
안드로이드 전자 메뉴판

Data Class & Enum
================
## Session
+ 서버 전송에 필요한 키값
>### 생성 경로
>+ 초기 화면 - 로그인

## Store Info
+ 가맹점 이름
>### 생성 경로
>+ 초기 화면 - 가맹점 정보 획득

## Order Info
+ 주문 번호
+ 주문 시간
+ 상품 목록 (List<OrderItem> - default emptyList)
+ 주문 상태 (Order Status - default 'None')
>### 생성 경로
>+ OrderInfosLiveData postValue
>+ 주문 화면 - 주문 전송

## Order Status (Enum)
+ Visible Message
>### 생성 경로 
>+ enum class definition

## ItemCategory
+ 카테고리 코드
+ 카테고리 이름
>### 생성 경로
>+ CategoryListLiveData postValue
  
## MenuItem
+ 상품 코드
+ 상품 이름
+ 상품 가격
+ 할인 가격
+ 섬네일 Url
+ 카테고리 코드 (List<String>)
>### 생성 경로
>+ ItemListLiveData postValue 
  


화면 구성도
=============
## 초기화면
### 주요 기능
1. 필요한 세션을 획득한다.
2. 가맹점 정보를 획득한다.
3. 메인화면으로 넘어간다.
3. 세션획득중 에러 발생시 해당 문구를 Notify (Error Type)으로 표시한다.
### 테스트 사항
1. invalid Login (only Api)
2. valid Login (only Api)
3. valid Get Store Info (Class Mapping Test) 
4. notify Check if Invalid Login
5. notify Check if Network Exception
6. notify Check if Uncaught Exception

## 메인화면
### 주요 기능
1. 현재 테이블에 대한 실시간 주문 감지 기능 (with Firebase Realtime Database)
2. 주문하기 (주문이 존재하는경우 "추가 주문"으로 텍스트 변경) 버튼 존재, 클릭시 주문화면으로 이동.
3. 직전 주문에 대한 상태 표시. 클릭시 현재 테이블의 전체 주문 상태 표시.
### 테스트 사항
1. order Button Text == "주문하기" if Order Empty
2. order Button Text == "추가주문" if Order Exist
3. last order status Check if Order Empty
4. last order status Check if Order Exist
