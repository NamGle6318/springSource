// todo의 체크박스를 체크시 post를 시도

// 체크박스 클릭이 되면
// 해당 체크박스의 값, data-id 가져오기
//

const todoList = document.querySelector(".list-group");

todoList.addEventListener("click", (e) => {
  // 체크박스 찾기
  let checkbox = e.target;

  // 체크박스의 체크 값 찾기
  console.log(checkbox.checked);

  // checkbox의 조상요소인 label 찾기
  let todo = checkbox.parentNode;
  // let todo = checkbox.closest("label")

  // 해당 체크박스가 있는 todo의 data-id 찾기
  const id = checkbox.closest("label").dataset.id;

  // actionForm 찾은 후 값 변경하기
  const actionForm = document.querySelector("#actionForm");
  actionForm.id.value = id;
  actionForm.finished.value = checkbox.checked;
  actionForm.submit();
});
