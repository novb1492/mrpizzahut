# mrpizzahut

학원에서 진행했던마지막 프로젝트입니다  
프로젝트기간 약20일  
장바구니/결제시스템/관리자페이지 전체를 전반적으로 작업했습니다  

결제 pg=세틀뱅크/카카오페이  
데이터베이스=oracle 11g  
ide=이클립스  
프레임워크= Spring Legercy  
클라우드=aws s3  

아쉬운점  
마이바티스로   
dto/validation을 사용하지 못하였습니다  
이유는
lombook을 사용할 수 없는 프로젝트 였고  
그밖의 개인적인 사정이 있었습니다  
전체적으로 하드코딩이 되어버려서 아쉽습니다  


알게된점  
늘 null검사 list가 비워있는지 검사할때  
list<t>형식이 달라서 고민이 많았습니다  
그래서 이번엔 제네릭을 한번 사용해보자 라는 생각을 하였고  
utillService에 형식에 상관없이 optional+제네릭을 이용해 null 검사와  
빈 배열 검사 메소드를 만들었습니다  

db정보
orders=주문이 관련 테이블입니다    
 Name                                      Null?    Type
 ----------------------------------------- -------- ----------------------------  
 ONUM                                      NOT NULL NUMBER  
 OEMAIL                                    NOT NULL VARCHAR2(100)  
 OMCHTTRDNO                                NOT NULL VARCHAR2(20)  
 ONAME                                     NOT NULL VARCHAR2(50)  
 OCOUNT                                    NOT NULL NUMBER  
 OCANCLEFLAG                               NOT NULL NUMBER  
 OCANCLEDATE                                        TIMESTAMP(6)  
 OCOUPONS                                           VARCHAR2(50)  
 OMETHOD                                   NOT NULL VARCHAR2(20)  
 OPRICE                                    NOT NULL NUMBER  
 OCREATED                                  NOT NULL TIMESTAMP(6)  
 ODONEFLAG                                 NOT NULL NUMBER  
 ODONEDATE                                          TIMESTAMP(6)  
 OSIZE                                              VARCHAR2(10)  
 OEDGE                                              VARCHAR2(30)  

CREATE SEQUENCE order_sq INCREMENT BY 1 START WITH 1  
UNIQUE (ONUM)  

kpay=카카오페이 관련 테이블입니다   
 Name                                      Null?    Type  
 ----------------------------------------- -------- ----------------------------  
 KNUM                                      NOT NULL NUMBER  
 KCID                                      NOT NULL VARCHAR2(50)  
 KCREATED                                  NOT NULL TIMESTAMP(6)  
 KPARTNERORDERID                           NOT NULL VARCHAR2(100)  
 KEMAIL                                    NOT NULL VARCHAR2(100)  
 KTEXFREE                                           NUMBER  
 KTID                                               VARCHAR2(50)  
 KPRICE                                    NOT NULL NUMBER  
 KDONEFLAG                                 NOT NULL NUMBER  
 KDONEDATE                                          TIMESTAMP(6)  
 KCANCLEFLAG                               NOT NULL NUMBER  
 KCANCLEDATE                                        TIMESTAMP(6)  
 KPHONE                                    NOT NULL VARCHAR2(11)  
 KCOUPN                                             VARCHAR2(300)  
 
 CREATE SEQUENCE kpay_sq INCREMENT BY 1 START WITH 1  
UNIQUE (KNUM)  
UNIQUE (KPARTNERORDERID)  
고유번호/고유거래번호 입니다  

vbank=가상계좌 거래관련 테이블입니다    
 Name                                      Null?    Type  
 ----------------------------------------- -------- ----------------------------  
 VNUM                                      NOT NULL NUMBER  
 VCREATED                                  NOT NULL TIMESTAMP(6)  
 VEXPIREDATE                                        TIMESTAMP(6)  
 VFNCD                                              VARCHAR2(100)  
 VFNNM                                              VARCHAR2(20)  
 VMCHTID                                            VARCHAR2(100)  
 VMCHTTRDNO                                NOT NULL VARCHAR2(50)  
 VMETHOD                                   NOT NULL VARCHAR2(10)  
 VTLACNTNO                                          VARCHAR2(30)  
 VTRDAMT                                   NOT NULL NUMBER  
 VTRDNO                                             VARCHAR2(50)  
 VCANCLEORD                                         NUMBER  
 VCANCLEFLAG                               NOT NULL NUMBER  
 VCANCLEDATE                                        TIMESTAMP(6)  
 VDONEFLAG                                 NOT NULL NUMBER  
 VDONEDATE                                          TIMESTAMP(6)  
 VPHONE                                    NOT NULL VARCHAR2(11)  
 VEMAIL                                    NOT NULL VARCHAR2(100)  
 VCOUPON                                            VARCHAR2(300)  
 CHECKFLAG                                 NOT NULL NUMBER  
 
UNIQUE (VNUM)  
UNIQUE (VMCHTTRDNO)  
고유번호/고유거래번호입니다  

card=카드관련 테이블입니다  
 Name                                      Null?    Type  
 ----------------------------------------- -------- ----------------------------  
 CNUM                                               NUMBER  
 CCANCLEFLAG                               NOT NULL NUMBER  
 CCNCLORD                                           NUMBER  
 CEMAIL                                    NOT NULL VARCHAR2(100)   
 CFNNM                                              VARCHAR2(50)  
 CMCHTTRDNO                                NOT NULL VARCHAR2(100)  
 CMCHTID                                            VARCHAR2(100)  
 CMETHOD                                   NOT NULL VARCHAR2(50)  
 CTRDAMT                                   NOT NULL NUMBER  
 CTRDNO                                             VARCHAR2(50)  
 CCANCLEDATE                                        TIMESTAMP(6)  
 CCREATED                                  NOT NULL TIMESTAMP(6)  
 CDONEFLAG                                 NOT NULL NUMBER  
 CDONEDATE                                          TIMESTAMP(6)  
 CPHONE                                    NOT NULL VARCHAR2(11)  
 CCOUPON                                            VARCHAR2(300)  
 
CREATE SEQUENCE card_sq INCREMENT BY 1 START WITH 1  
UNIQUE (CNUM)  
UNIQUE (CMCHTTRDNO)  

coupon=쿠폰 관련 테이블입니다    
 Name                                      Null?    Type  
 ----------------------------------------- -------- ----------------------------
 CONUM                                     NOT NULL NUMBER  
 COUSEDEMAIL                                        VARCHAR2(100)  
 COUPONNAME                                NOT NULL VARCHAR2(200)  
 USEDFLAG                                  NOT NULL NUMBER  
 USEDDATE                                           TIMESTAMP(6)  
 COMCHTTRDNO                                        VARCHAR2(200)  
 COKIND                                    NOT NULL VARCHAR2(20)    
 COPRICE                                   NOT NULL NUMBER  
 COCREATED                                 NOT NULL TIMESTAMP(6)  
 COEXPIRED                                 NOT NULL TIMESTAMP(6)  
 
CREATE SEQUENCE coupon_sq INCREMENT BY 1 START WITH 1  
UNIQUE (CONUM)  
UNIQUE (COUPONNAME)  
고유번호/쿠폰이름을 유니크로 지정했습니다  

구현기능
 
유저페이지

결제
카드/가상계좌/카카오페이결제  
쿠폰 중복/기간/개수검증  
(다른 제품에 동일 쿠폰 이있어도 검증가능)  
(피자 3판이면 쿠폰은 최대 3개만 가능)  
일괄 적용이 아닌 판수에 맞게 적용  
(a피자 3판 쿠폰 1개 라면 a피자 첫번째 판에대해서만 쿠폰 적용)  

재고 검증  
장바구니 
수량 조절 및 계산  

관리자페이지
메뉴등록/수정/삭제 가능  
메뉴이미지는 모두 s3에 저장  
글작성중 사진 업로드시 세션에저장
저장 하지 않고 이탈시 사진이 존재하면  
클라우드에서 자동 삭제가 이뤄짐  

쿠폰등록/수정/삭제가능  
주문 조회/환불가능  
환불시 쿠폰도 다시 살려주게 해놓았습니다  

게시판 로직 처럼만들어서  
페이징/검색도 가능  

매출차트  
연도/월/일/품목별로  순판매금액/환불금액 막대차트로 표시  




 

 


 
 

