// 등록 버튼을 누르면 submit은 하지 않고
// li 태그에 있던 data 정보들을 수집 후 폼에 추가
// 폼을 전송

const addBtn = document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  const output = document.querySelectorAll(".uploadResult li");
  let result = ``;

  output.forEach((obj, idx) => {
    // name, path, uuid 정보 수집 => form에 담기
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}"></input>`;
  });
  e.target.insertAdjacentHTML("beforeend", result);

  e.target.submit();
});
