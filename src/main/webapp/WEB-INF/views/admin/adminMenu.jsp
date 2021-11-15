<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common/adminHeader.jsp" %>
  <%@ include file="../common/adminSide.jsp" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>
</head>
<body>
<div class="contents">
<form  id="minsert" name="minsert">
메뉴제목:
<br>
<input type="text" name="title" class="form-control menuInput mt-2" placeholder="메뉴제목을 적어주세요">
<br>
메뉴 STITLE:
<br>
 <input type="text" name="stitle" class="form-control menuInput mt-2" placeholder="메뉴stitle을 적어주세요">
<br>
메뉴 ITITLE: 
<br>
<input type="text" name="ititle" class="form-control menuInput mt-2" placeholder="메뉴ititle을 적어주세요">
<br>
메뉴 사이즈 
<br>
대문자로 , 로 나누어서 적어주세요 EX=(R,S,L,M)
<br>
<input type="text" name="size" class="form-control menuInput mt-2" placeholder="사용가능 사이즈를 적어주세요">
<br>
메뉴 엣지
<br>
한글로 ,로 나누어서 적어주세요 ex=(오리진,고구마)
<br>
<input type="text" name="edge" class="form-control menuInput mt-2" placeholder="사용가능 엣지를 적어주세요">
메뉴 내용 
<br>
<textarea style="wnameth: 650px; height: 300px" id="editor"></textarea>
메뉴 이미지
<br>
<input type="file" name="upload" multiple>
<br>
메뉴가격
<br>
구분 표시를 ,로 해주세요 옳은 예 1,000 틀린예 1000
<br>
<input type="text" name="price" class="form-control menuInput mt-2" placeholder="가격을 적어주세요">
<br>
일 최대 판매량을 입력해주세요
<br>
<input type="number" min="1" name="count" class="form-control menuInput mt-2" placeholder="일 최대 판매량을 입력해주세요">
<br>
<input type="button"  class="form-control menuInput mt-2" value="메뉴저장" onclick="insertMenu('minsert')">
</form>
</div>
<script type="text/javascript">
let editor;
var flag=true;
/*function insert() {
	flag=false;
	 let data=JSON.stringify({
		 "text":editor.getData(),
		 "title":getnameValue('title'),
		 "stitle":getnameValue('ititle'),
		 "size":getnameValue('size'),
		 "edge":getnameValue('edge'),
		 "img":getnameValue('productImg'),
		 "price":getnameValue('price')
	});
	var result=requestToFormPost('/app/admin/insert',data);
	alert(result.message);
	if(result.flag){
		location.reload();
	}
}*/
class MyUploadAdapter {
    constructor(props) {
        // CKEditor 5's FileLoader instance.
      this.loader = props;
      // URL where to send files.
      this.url = '/app/img';
    }

    // Starts the upload process.
    upload() {
        return new Promise((resolve, reject) => {
            this._initRequest();
            this._initListeners(resolve, reject);
            this._sendRequest();
        } );
    }

    // Aborts the upload process.
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // Example implementation using XMLHttpRequest.
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        xhr.open('POST', this.url, true);
        xhr.responseType = 'json';

    }

    // Initializes XMLHttpRequest listeners.
    _initListeners( resolve, reject ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = 'Couldn\'t upload file:' + ` ${ loader.file.name }.`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;
            if ( !response || response.error ) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            // If the upload is successful, resolve the upload promise with an object containing
            // at least the "default" URL, pointing to the image on the server.
            resolve({
                default: response.url
            });
        } );

        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    // Prepares the data and sends the request.
    _sendRequest() {
        const data = new FormData();

        this.loader.file.then(result => {
          data.append('upload', result);
          //this.xhr.setRequestHeader('Content-type', 'multipart/form-data');
          this.xhr.send(data);
          }
        )
    }

}
function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
    // Configure the URL to the upload script in your back-end here!
    return new MyUploadAdapter( loader );
    };
}
ClassicEditor
.create( document.querySelector('#editor'), {
        extraPlugins: [ MyCustomUploadAdapterPlugin ],

        // ...
    } )
	.then( newEditor  => {
        console.log( 'Editor was initialized', newEditor  );
        editor = newEditor ;
    } )
	.catch( error => {
	   
} );

</script>	
</body>
</html>