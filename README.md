# java-convenience-store-precourse
## 기능 요구 사항
### 1. 편의점 보유 재고 관리
- 각 상품의 재고 수량을 고려하여 결제 가능 여부를 확인한다.
- 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감하여 수량을 관리한다.
- 재고를 차감함으로써 시스템은 최신 재고 상태를 유지하며, 다음 고객이 구매할 때 정확한 재고 정보를 제공한다.
#### (1) 보유 상품 안내 문구를 출력한다
````
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.
````
출력할 문구는 위와 같다.
#### (2) 보유 상품 목록 불러온다
`src/main/resources/products.md` 파일을 읽어 보유 상품 목록을 불러온다. 그 이후 데이터를 저장한다.
````
name,price,quantity,promotionEntity
콜라,1000,10,탄산2+1
콜라,1000,10,null
사이다,1000,8,탄산2+1
사이다,1000,7,null
오렌지주스,1800,9,MD추천상품
탄산수,1200,5,탄산2+1
물,500,10,null
비타민워터,1500,6,null
감자칩,1500,5,반짝할인
감자칩,1500,5,null
초코바,1200,5,MD추천상품
초코바,1200,5,null
에너지바,2000,5,null
정식도시락,6400,8,null
컵라면,1700,1,MD추천상품
컵라면,1700,10,null

````
불러올 파일의 형식은 위와 같이 "name,price,quantity,promotionEntity"으로 ',' 단위로 구성되어 있다.

파일이 잘못됐을 경우 에러를 발생시키고 다음 경우에 수에 맞게 에러 메시지를 출력한다.
1. 파일 로딩 실패 : IO Exception, `[ERROR] 파일을 불러올 수 없습니다.`
2. 파일 형식 불일치 : IllegalArgumentException, `[ERROR] 파일의 형식이 올바르지 않습니다.`

#### (3) 보유 상품 목록을 출력한다
````
- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개
````
형식은 위와 같이 "-name,price,quantity,promotionEntity" 으로 출력한다.  
이 때, 재고가 0개(null)라면 `재고 없음`을 출력한다.

### 3. 구매할 편의점 상품 입력
#### (1) 구매할 상품과 수량을 입력 받는다.
````
구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-10],[사이다-3]
````
형식은 위와 같이 "[상품명-수량],[상품명-수량],...." 으로 입력 받는다.
#### (2) 입력 받은 값이 형식에 맞는지 검증한다.
다음 조건들을 검사한다.
1. null이나 empty 값이 아닌가?
2. (1)에서 제시한 형식에 부합하는가?

사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
- 구매할 상품과 수량 형식이 올바르지 않은 경우: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
- 기타 잘못된 입력의 경우: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`
#### (3) 재고가 있는지 확인한다.
사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
- 존재하지 않는 상품을 입력한 경우: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
- 구매 수량이 재고 수량을 초과한 경우: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`

### 4. 구매한 상품 프로모션 적용 확인
#### (1) 프로모션을 불러온다.
`src/main/resources/promotions.md` 파일을 읽어 행사 목록을 불러온다. 그 이후 데이터를 저장한다.
````
name,buy,get,start_date,end_date
탄산2+1,2,1,2024-01-01,2024-12-31
MD추천상품,1,1,2024-01-01,2024-12-31
반짝할인,1,1,2024-11-01,2024-11-30
````
불러올 파일의 형식은 위와 같이 "name,buy,get,start_date,end_date"으로 ',' 단위로 구성되어 있다.

파일이 잘못됐을 경우 에러를 발생시키고 다음 경우에 수에 맞게 에러 메시지를 출력한다.
1. 파일 로딩 실패 : IO Exception, `[ERROR] 파일을 불러올 수 없습니다.`
2. 파일 형식 불일치 : IllegalArgumentException, `[ERROR] 파일의 형식이 올바르지 않습니다.`

#### (2) 구매한 상품들에 맞게 프로모션을 적용한다.
- 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다.
- 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태로 진행된다.
- 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
- 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
- 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

이와 같은 조건들 고려하여 구매할 상품에 대한 프로모션을 적용한다.
#### (3) 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 안내하고 입력받는다
````
현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y
````
- Y: 증정 받을 수 있는 상품을 추가한다.
- N: 증정 받을 수 있는 상품을 추가하지 않는다.
  
'Y' 와 'N' 외의 입력 받을 시
`"[ERROR] Y와 N만 입력할 수 있습니다."` 메시지 출력과 함께 `IllegalArgumentException` 발생시키고 다시 입력을 받는다. 

#### (4) 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 안내하고 입력받는다
````
현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
Y
````
- Y: 일부 수량에 대해 정가로 결제한다.
- N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.

'Y' 와 'N' 외의 입력 받을 시 `"[ERROR] Y와 N만 입력할 수 있습니다."` 메시지 출력과 함께 `IllegalArgumentException` 발생시키고 다시 입력을 받는다.
### 4. 멤버쉽 할인 적용
#### (1) 멤버십 할인 적용 여부 안내 문구를 출력한다.
````
멤버십 할인을 받으시겠습니까? (Y/N)
````
위 문구를 출력한다.
#### (2) 멤버쉽 할인 적용 여부를 입력 받는다.
````
Y
````
- Y: 멤버십 할인을 적용한다.
- N: 멤버십 할인을 적용하지 않는다.

#### (3) 멤버쉽 할인 적용 여부 입력값을 검증한다.
'Y' 와 'N' 외의 입력 받을 시 `"[ERROR] Y와 N만 입력할 수 있습니다."` 메시지 출력과 함께 `IllegalArgumentException` 발생시키고 다시 입력을 받는다.
#### (4) Y를 입력 받은 경우, 멤버쉽 할인을 적용한다.
- 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
- 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
- 멤버십 할인의 최대 한도는 8,000원이다.

### 5. 물품을 구매
#### (1) 구매 처리를 위한 데이터를 계산한다.
필요한 데이터는 다음과 같다.
1. 구매한 상품들의 이름, 수량, 총 가격
2. 증정한 상품들의 이름, 수량
3. 총 수량과 총 구매액
4. 행사할인 금액
5. 멤버쉽할인 금액
6. 총 낼 돈
#### (2) 계산된 데이터를 6번을 기능을 위해 저장한다.

#### (3) 상품을 구매 처리한다

### 6. 영수증 출력
#### (1) 영수증 내역에 필요한 데이터를 가져온다.
5-1 에서 계산된 데이터들을 영수증 내역에 사용한다.


#### (2) 영수증 내역을 출력한다.
````
===========W 편의점=============
상품명		수량	금액
콜라		10 	10,000
===========증	정=============
콜라		2
==============================
총구매액		10	10,000
행사할인			-2,000
멤버십할인			-0
내실돈			 8,000
````
위와 같이 출력하되 다음을 주의한다.
1. 행사할인과 맴버쉽할인은 0원이라도 -로 표시한다.

### 6. 추가 구매 여부를 입력
#### (1) 추가 구매 여부 안내 문구를 출력한다
````
감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
````
위와 같이 출력한다.
#### (2) 추가 구매 여부를 입력 받는다
- Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다. 이 때, 2번 기능으로 돌아간다.
- N: 구매를 종료한다.

'Y' 와 'N' 외의 입력 받을 시 `"[ERROR] Y와 N만 입력할 수 있습니다."` 메시지 출력과 함께 `IllegalArgumentException` 발생시키고 다시 입력을 받는다.