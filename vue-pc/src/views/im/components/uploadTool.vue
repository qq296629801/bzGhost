<template>
  <div class="upload-tool">
    <Upload
      :action="action"
      name="file"
      :format="imgFormat"
      :data="tokenImg"
      :show-upload-list="false"
      :headers="headers"
      :max-size="5120"
      :with-credentials="true"
      :before-upload="beforeUpload"
      :on-progress="handleStart"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleImgMaxSize"
      :on-success="handleSuccess"
      :on-error="handleError"
    >
      <Icon type="ios-image-outline"></Icon>
    </Upload>
    <Upload
      :action="action"
      name="file"
      :format="fileFormat"
      :data="tokenFile"
      :show-upload-list="false"
      :headers="headers"
      :max-size="102400"
      :with-credentials="true"
      :before-upload="beforeUpload"
      :on-progress="handleStart"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleFileMaxSize"
      :on-success="handleSuccess"
      :on-error="handleError"
    >
      <Icon type="ios-folder-open-outline"></Icon>
    </Upload>
  </div>
</template>

<script>
import base from "@/utils/baseUrl.js";
export default {
  name: "upload-tool",
  data() {
    return {
      imgFormat: ["jpg", "jpeg", "png", "gif"],
      fileFormat: [
        "doc",
        "docx",
        "jpg",
        "jpeg",
        "png",
        "gif",
        "xls",
        "xlsx",
        "pdf",
        "gif",
        "exe",
        "msi",
        "swf",
        "sql",
        "apk",
        "psd"
      ],
      tokenImg: {
        access_token: '',
        type: "image"
      },
      tokenFile: {
        access_token: '',
        type: "file"
      },
      action: base.baseUrl + "file/upload",
      headers: {
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
      }
    };
  },
  methods: {
    beforeUpload() {
      this.tokenImg = {
        access_token: '',
        type: "image"
      };
      this.tokenFile = {
        access_token: '',
        type: "file"
      };
      return new Promise(resolve => {
        this.$nextTick(function() {
          resolve(true);
        });
      });
    },

    // 错误提示
    openMessage(error) {
      this.$Message.error(error);
    },

    handleStart() {
      this.$Loading.start();
    },
    handleFormatError(file) {
      this.$Message.warning("文件 " + file.name + " 格式不正确。");
    },
    handleImgMaxSize(file) {
      this.$Message.warning("图片 " + file.name + " 太大，不能超过 512K！");
    },
    handleFileMaxSize(file) {
      this.$Message.warning("文件 " + file.name + " 太大，不能超过 10M！");
    },
    handleSuccess(res, file) {
      let self = this;
      if (res.msg === "success") {
        let path = res.filePath;
        let fileName = file.name;
        // 文件后缀
        let suffix = fileName.substring(
          fileName.lastIndexOf(".") + 1,
          fileName.length
        );

        //上传文件路径
        let url = "";
        // 文件
        if (self.imgFormat.indexOf(suffix) === -1) {
          url = "file(" + path + ")[" + fileName + "]";
        }
        // 图片
        else {
          url = "img[" + path + "]";
        }
        //给父类传值
        self.$emit("uploadBack", url);

        this.$Loading.finish();
      } else {
        this.$Message.error("文件上传错误，请重试");
      }
    },
    handleError: function() {
      this.$Loading.finish();
      this.$Message.error("上传错误！");
    }
  }
};
</script>

<style scoped>
.upload-tool {
  display: inline-block;
}
</style>
