# Chap02 - 미션

![내가 진행중, 진행 완료한 미션 모아서 보는 쿼리(페이징 포함)](Chap02%20-%20%E1%84%86%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB%20110328105aa680b9b22bde6386ca27ef/Untitled.png)

내가 진행중, 진행 완료한 미션 모아서 보는 쿼리(페이징 포함)

select r.name, m.rewardPoint, m.minOrderPrice, m.isChallenged from mission m join restaurant r on m.restaurant.id = r.id where m.membeId = x and m.isChallenged in (’IN_PROGRESS’, ‘COMPLETE’) limit 10 offset 0;

---

![리뷰 작성하는 쿼리,
* 사진의 경우는 일단 배제](Chap02%20-%20%E1%84%86%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB%20110328105aa680b9b22bde6386ca27ef/Untitled%201.png)

리뷰 작성하는 쿼리,
* 사진의 경우는 일단 배제

- 리뷰 작성

insert into review (memberId, restaurantId, score, content, createdAt) values (1, 1, 5 ‘맛있어요!’, now());

- 이미지 저장

insert into image (reviewId, url) values(1, ‘https://bucket.s3.ap-northeast2.amazonaws.com/0d46ce1c-7cb2-4290-b6a5-732d9cd1256f.png’);

---

![홈 화면 쿼리
(현재 선택 된 지역에서 도전이 가능한 미션 목록, 페이징 포함)](Chap02%20-%20%E1%84%86%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB%20110328105aa680b9b22bde6386ca27ef/Untitled%202.png)

홈 화면 쿼리
(현재 선택 된 지역에서 도전이 가능한 미션 목록, 페이징 포함)

select r.name, r.type, m.rewardPoint, m.minOrderPrice, m.deadLine from mission m join restaurant r on m.restaurant.id = r.id join region rg on r.regionId = rg.id where m.memberId is null and m.isChallenged = ’PENDING’ and rg.depth3 = ‘안암동’ limit 10 offset 0;

---

![마이 페이지 화면 쿼리](Chap02%20-%20%E1%84%86%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB%20110328105aa680b9b22bde6386ca27ef/Untitled%203.png)

마이 페이지 화면 쿼리

select name, email, phoneNumber, totalPoint from member where id = 1;