说明：
文件管理模块可用于管理服务器中用户上传的文件或作为临时文件中转使用，该模块提供文件上传接口，为后台提供文件添加服务，提供文件下载服务等
考虑到文件下载的安全性，模块不对外提供下载接口或下载页面，在使用该模块的其它模块中，请引入该service并调用downloadfile函数进行下载。

如何添加功能
1. 将modules/filemanager/src目录设置为源码目录（在src上右键Mark Direction as...)
2. 进入File->Project Structure->Modules->SH(WEB右侧) 在web resources directies添加modules\filemanager\web-->/

文件上传接口
http://ip:port/SH/uploadfiles
文件查看页面
http://ip:port/SH/filemanager

文件管理服务接口
    使用方法： 在需要使用的后台代码中，添加以下代码引入Service的bean
    @Resource(name="fileManagerService")
    private FileManagerService fileManagerService;

