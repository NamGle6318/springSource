const reviewForm = document.querySelector("#reviewForm");
const reviewCnt = document.querySelector(".review-cnt");

// 날짜 형식 지정
const formatDate = (str) => {
  const date = new Date(str);
  console.log();
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};
const reviewList = () => {
  // 댓글 정보 불러오기

  axios.get(`/reviews/${mno}/all`).then((res) => {
    const data = res.data;

    const reviewDiv = document.querySelector(".reviewList");
    reviewCnt.innerHTML = data.length;

    let result = ``;

    data.forEach((review) => {
      result += ` <div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno="${review.rno}" data-email="${review.email}">`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div><span class="font-semibold">${review.text}</span> </div>`;
      result += `<div class="text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
      result += `평점 : <span class="grade">${review.grade}</span> <div class="starrr"> </div> </div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;

      result += `<div class="mb-2">`;
      if (loginUser == review.email) {
        result += `<button class="btn btn-outline-danger btn-sm">삭제</button>`;
        result += `</div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div></div></div></div>`;
    });
    reviewDiv.innerHTML = result;
  });
};

reviewList();

// 리뷰 삭제, 수정
document.querySelector(".reviewList").addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".review-row").dataset.rno;

  // 리뷰작성자의 이메일
  const email = btn.closest(".review-row").dataset.email;

  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    const form = e.target;

    axios
      .delete(`/reviews/${mno}/${rno}`, {
        // data: { replyerEmail: email },
        headers: {
          "X-CSRF-TOKEN": csrf,
          //   "Content-Type": "application/json",
        },
      })
      .then((res) => {
        //삭제 후 댓글 리로드
        reviewList();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // TODO: 수정할 댓글 작성하는 기능 구현

    axios.get(`/reviews/${mno}/${rno}`).then((res) => {
      const data = res.data;
      console.log(reviewForm);
      // console.log(data);

      reviewForm.rno.value = data.rno;
      reviewForm.text.value = data.text;
      reviewForm.nickname.value = data.nickname;
      reviewForm.email.value = data.email;
      reviewForm.mid.value = data.mid;

      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
    });
  }
});

reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;

  const rno = form.rno.value;
  form.grade.value = grade;

  if (rno) {
    // 수정

    const data = {
      rno: form.rno.value,
      text: form.text.value,
      grade: form.grade.value,
    };
    axios
      .put(`/reviews/${mno}/${rno}`, data, {
        headers: {
          "X-CSRF-TOKEN": csrf,
          // "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 수정 완료");
        reviewForm.rno.value = "";
        // reviewForm.nickname.value = "";

        reviewForm.mid.value = "";
        reviewForm.text.value = "";
        reviewForm.grade.value = 0;
        reviewList();
      });
  } else {
    // 댓글 삽입

    const data = {
      text: form.text.value,
      grade: form.grade.value,
      mid: form.mid.value,
      mno: form.mno.value,
    };
    console.log(data);

    axios
      .post(`/reviews/${mno}`, data, {
        headers: {
          "X-CSRF-TOKEN": csrf,
          // "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        reviewForm.rno.value = "";
        // reviewForm.nickname.value = "";

        // reviewForm.mid.value = "";
        reviewForm.text.value = "";
        reviewForm.grade.value = 0;
        reviewList();
      });
  }
});
// end
