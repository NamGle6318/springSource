const showUploadImages = (arr) => {
  const output = document.querySelector("#output");

  arr.forEach((element) => {
    // display = backend's root주소
    output.insertAdjacentHTML("beforeend", `<img src="/upload/display?fileName=${element.imageURL}">`);
  });
};

document.querySelector("button").addEventListener("click", () => {
  // 버튼 클릭시 uploadFiles 가져오기
  const inputFile = document.querySelector("[name=uploadFiles]");
  const files = inputFile.files;
  console.log("inputFile : ", inputFile);
  console.log("files : ", files);

  // form 생성 후 업로드 된 파일을 append하기

  // form 생성
  let form = new FormData();

  // form 안에 file 넣기
  for (let i = 0; i < files.length; i++) {
    form.append("uploadFiles", files[i]);
  }

  axios
    .post("/upload/files", form, {
      headers: {
        "X-CSRF-TOKEN": csrf,
      },
    })
    .then((res) => {
      console.log(res.data);
      showUploadImages(res.data);
    })
    .catch(function (error) {
      console.log("error", error);
    });
});
