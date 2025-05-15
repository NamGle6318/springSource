// test
document.querySelector(".move").classList.forEach((c) => console.log(c));

// 삭제 클릭시 removeForm 전송
document.querySelector(".move").addEventListener("click", (e) => {
  // a 태그 기능 중지
  e.preventDefault();

  document.querySelector("#removeForm").submit();
});

// window.formBtn = function () {
//   document.querySelector("#removeForm").submit();
// };
