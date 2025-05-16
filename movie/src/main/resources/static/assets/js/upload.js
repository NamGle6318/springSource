const fileInput = document.querySelector("[name=file]");

const showUploadImages = (arr) => {
  const uploadResult = document.querySelector(".uploadResult ul");
  let tags = "";

  console.log(" ------------arr");
  console.log(arr);

  // 이미지 화면에 출력 및 삭제 a태그 생성
  arr.forEach((element, idx) => {
    tags += `<li data-name="${element.fileName}" data-path="${element.folderPath}" data-uuid="${element.uuid}">`;
    tags += `<img src="/upload/display?fileName=${element.thumbnailImageURL}">`;
    tags += `<a href="${element.imageURL}"> <i class="fa-regular fa-circle-xmark"></i></a>`; // href에 있는건 그냥 값 저장용
    tags += `</li>`;
  });

  uploadResult.insertAdjacentHTML("beforeend", tags);
};

document.querySelector(".uploadResult").addEventListener("click", (e) => {
  // 버튼 클릭시 uploadFiles 가져오기
  e.preventDefault();
  const liTag = e.currentTarget;

  const aTag = e.target.closest("a");
  const fileName = aTag.getAttribute("li");
  console.log(aTag);
  console.log(fileName);
  let form = new FormData();
  form.append("fileName", fileName);
  if (!confirm("정말로 삭제하시겠습니까?")) {
    return;
  }
  axios
    .post("/upload/remove", form, {
      headers: {
        "X-CSRF-TOKEN": csrf,
      },
    })
    .then((res) => {
      console.log(res.data);
      liTag.remove();
    });
});

fileInput.addEventListener("change", (e) => {
  // 버튼 클릭시 uploadFiles 가져오기
  const inputFile = e.target;
  const files = inputFile.files;

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
    });
});
