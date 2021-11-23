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
orders=주문이 이뤄지면 주문정보가 담기는 db  
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

kpay=카카오페이 결제시 저장이 이뤄집니다  
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
 
 


 
 

