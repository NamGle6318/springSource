// 삭제버튼 누르면 actionform을 submit

const deleteBtn = document.querySelector(".btn-danger");

deleteBtn.addEventListener("click", (e) => {
  const actionForm = document.querySelector("#actionForm");
  actionForm.submit();
});
