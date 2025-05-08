// 보드를 읽으러 들어가면 해당 보드의 댓글들 불러오기

// 형태
// axios.get().then().catch().finally();
// axios.post().then().catch().finally();
// axios.put().then().catch().finally();
// axios.delete().then().catch().finally();

// date

// 댓글창 출력
const replyList = (data) => {
  let result = "";

  // 댓글창에 출력할 데이터 형식 지정
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

  data.forEach((reply) => {
    // let date = formatDate(reply.createdDate);
    result += ` <div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
    result += `<div class="p-3">`;
    result += `<img
                src="/img/default.png"
                alt=""
                class="rounded-circle mx-auto d-block"
                style="width: 60px; height: 60px"
              />`;
    result += `</div>`;
    result += `<div class="flex-grow-1 align-self-center">`;
    result += `<div>${reply.replyer}</div>`;
    result += `<div><span class="fs-5">${reply.text}</span></div>`;
    result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div>`;
    result += `</div>`;
    result += `<div class="d-flex flex-column align-self-center">`;
    result += `<div class="mb-2">`;
    result += `<button class="btn btn-outline-danger btn-sm">삭제</button>`;
    result += `</div>`;
    result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
    result += `</div></div></div></div></div>`;
  });

  document.querySelector(".replyList").innerHTML = result;
};

// 댓글 리스트 생성
axios.get(`/replies/board/${bno}`).then((res) => {
  console.log(res.data);

  let data = res.data;
  replyList(data);
});

// 댓글의 버튼 입력 감지(삭제, 수정)
document.querySelector(".replyList").addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".reply-row").dataset.rno;

  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    axios.delete(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      //삭제 후 댓글 리로드
    });
  } else if (btn.classList.contains("btn-outline-success")) {
    // TODO: 수정할 댓글 작성하는 기능 구현

    axios.put(`/replies/${rno}`).then((res) => {
      console.log(res.data);
    });
  }
});
