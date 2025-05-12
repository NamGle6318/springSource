// 보드를 읽으러 들어가면 해당 보드의 댓글들 불러오기

// axios()형태
// axios.get().then().catch().finally();
// axios.post().then().catch().finally();
// axios.put().then().catch().finally();
// axios.delete().then().catch().finally();

// replyForm
const replyForm = document.querySelector(".replyForm");

// 현재 접속중인 user's Email
const currentUserEmail = document.querySelector("meta[name=current-user-email]");
// 현재 접속중인 user's RoleType의 값
const currentUserRoleType = document.querySelector("meta[name=current-user-roleType]");
const UserRoleTypeValues = currentUserRoleType.getAttribute("roleType");
// User ROLETYPE 확인
const checkRoleType = UserRoleTypeValues.includes("ROLE_ADMIN") || UserRoleTypeValues.includes("ROLE_MANAGER");


// 댓글창 출력
const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    let data = res.data;

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
    // 댓글 리스트 출력 html
    data.forEach((reply) => {
      
      
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
      result += `<div>${reply.replyerName}</div>`;
      result += `<div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div>`;
      result += `</div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      result += `<div class="mb-2">`;
      if (currentUserEmail.content == reply.replyerEmail || checkRoleType) {
        result += `<button class="btn btn-outline-danger btn-sm">삭제</button>`; 
        result += `</div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`; 
      }
      result += `</div></div></div></div>`;
    });

    document.querySelector(".replyList").innerHTML = result;

    // replyCount
    const replyCount = document.querySelector("h5 > .cnt");
    replyCount.innerHTML = data.length;
  });
};

// 댓글 리스트 생성
replyList();

// st
replyForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;

  if (rno) {
    // 수정
    console.log(form);
    axios
      .put(`/replies/${rno}`, form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 수정 완료");
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";

        // todo 댓글 리스트 재 호출
        replyList();
      });
  } else {
    // todo 삽입
    axios
      .post("/replies/new", form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
      });
    replyList();
  }
});
// end

// 댓글의 버튼 입력 감지(삭제, 수정)
document.querySelector(".replyList").addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".reply-row").dataset.rno;

  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    axios.delete(`/replies/${ rno}`).then((res) => {
      //삭제 후 댓글 리로드
    });
    replyList();
  } else if (btn.classList.contains("btn-outline-success")) {
    // TODO: 수정할 댓글 작성하는 기능 구현

    axios.get(`/replies/${rno}`).then((res) => {
      const data = res.data;
    
      replyForm.rno.value = data.rno;
      replyForm.replyer.value = data.replyerName;
      replyForm.text.value = data.text;
    });
  }
});
