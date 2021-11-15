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
메뉴제목:<input type="text" id="title" class="btn btn-outline-primary btn-sm" placeholder="메뉴제목을 적어주세요">
<br>
메뉴 내용 
<br>
<textarea style="width: 650px; height: 300px" id="editor"></textarea>

메뉴 이미지
<input type="file" id="productImg" multiple>

<input type="button"  class="btn btn-outline-primary btn-sm" value="메뉴저장" onclick="insert()">
</div>
<script type="text/javascript">
let editor;
var flag=true;
function insert() {
	flag=false;
	 let data=JSON.stringify({
		 "text":editor.getData(),
		 "title":getIdValue('title')
	});
	var result=requestToServer('/app/admin/insert',data);
	alert(result.message);
	if(result.flag){
		location.href='/demo4/boardPage?page=1';
	}else{
		flag=true;
	}
}
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