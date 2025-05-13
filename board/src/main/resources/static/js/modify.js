// 삭제 버튼 클릭시 removeForm submit
const removeBtn = document.querySelector(".btn-danger");
removeBtn.addEventListener("click", () => {
  // removeForm
  document.querySelector("#removeForm").submit();
});
