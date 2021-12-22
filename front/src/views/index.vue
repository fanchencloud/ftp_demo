<template>
  <div class="container">
    <div class="menuDiv">
      <!--<n-button type="info" class="myButton" @click="getFileList">getFileList</n-button>-->
      <n-button type="info" class="myButton" @click="redeploy">重新部署</n-button>
      <!--<n-button type="info" class="myButton" @click="addTest">add</n-button>-->
      <n-button type="primary" class="myButton" @click="logout">退出登录</n-button>
    </div>
    <div class="navigation">
      <n-breadcrumb>
        path:
        <n-breadcrumb-item href="#" v-for="(item, index) in filePath" :key="index" @click="selectPath(index)">
          {{ item }}
        </n-breadcrumb-item>
      </n-breadcrumb>
    </div>
    <div class="fileListContainer" v-show="!showUploadDiv">
      <n-data-table
          remote
          ref="table"
          :columns="columns"
          :data="fileData"
          :loading="loading"
          :pagination=false
          :row-key="row => row.name"
          :row-props="rowProps"
      />
    </div>
    <div class="fileListContainer" v-show="showUploadDiv">
      <n-upload action="http://localhost:8988/file/upload" :default-upload="false" v-model:file-list="uploadList"
                @change="handleChange" @before-upload="beforeUpload">
        <n-upload-dragger>
          <div style="margin-bottom: 12px;">
            <n-icon size="48" :depth="3">
              <archive-icon/>
            </n-icon>
          </div>
          <n-text style="font-size: 16px;">点击或者拖动文件到该区域来上传</n-text>
          <n-p depth="3" style="margin: 8px 0 0 0;">
            请不要上传敏感数据，比如你的银行卡号和密码，信用卡号有效期和安全码
          </n-p>
        </n-upload-dragger>
      </n-upload>
      <n-button type="info" @click="uploadFile" :disabled="!fileListLength">上传</n-button>
    </div>
  </div>
</template>

<script>
import {NBreadcrumb, NBreadcrumbItem, NButton, NDataTable, NUpload, NUploadDragger, NIcon, NText, NP,} from "naive-ui";
import {logoutApi} from "../apis/login";
import {getFileListApi, redeployApi, uploadFileApi} from "../apis/file";
import {ArchiveOutline as ArchiveIcon} from '@vicons/ionicons5'
import axios from "axios";

const {$message: Message, $dialog: Dialog} = window;

export default {
  name: "index",
  components: {
    NBreadcrumb,
    NBreadcrumbItem,
    NButton,
    NDataTable,
    NUpload,
    NUploadDragger,
    NIcon,
    NText,
    NP,
    ArchiveIcon
  },
  data() {
    return {
      // 文件路径
      filePath: [],
      columns: [
        {
          title: '文件名',
          key: 'name'
        }, {
          title: '文件类型',
          key: 'extensionName'
        }, {
          title: '创建时间',
          key: 'createTime'
        }, {
          title: '文件大小',
          key: 'size'
        },
      ],
      fileData: [],
      loading: false,
      showUploadDiv: false,
      uploadList: [],
      fileListLength: 0,
      canUpload: false,
    }
  },
  mounted() {
    this.filePath.push('~')
  },
  computed: {},
  methods: {
    redeploy() {
      const _this = this
      Dialog.warning({
        title: '重新部署',
        content: '重新部署将清空服务器上的文件，并需要重新上传文件',
        positiveText: '确定',
        negativeText: '取消',
        bordered: false,
        showIcon: true,
        maskClosable: false,
        onPositiveClick: () => {
          // 发送清空文件请求
          redeployApi()
              .then(res => {
                console.debug(res)
                _this.showUploadDiv = true
              })
              .catch(err => {
                console.error(err)
              })

        },
        onNegativeClick: () => {
          console.debug('取消')
        }
      })
    },
    logout() {
      const _this = this
      logoutApi().then(res => {
        _this.$router.push('/login')
      })
    },
    addTest() {
      this.filePath.push("haha" + Math.round(Math.random() * 80 + 20))
    },
    selectPath(index) {

      this.filePath.length = index + 1
    },
    // 列表展示
    handleCheck(row) {
      this.filePath.push(row.name)
    },
    // 文件查询
    getFileList() {
      this.loading = true
      // 获取当前路径
      let filePath = ''
      this.filePath.forEach(path => {
        filePath = filePath + '/' + path
      })
      filePath = filePath.replace("/~", "")

      const _this = this
      getFileListApi(filePath)
          .then(res => {
            console.debug(res)
            const {code, data} = res
            if (code === 200) {
              _this.fileData = data.fileList
            } else {
              Message.warning("数据请求失败")
            }
            _this.loading = false
          })
          .catch(err => console.error(err))
    },

    rowProps(row) {
      const _this = this
      return {
        style: 'cursor: pointer;',
        onClick: () => {
          // 是文件夹，可以继续进入
          if (row.directory) {
            _this.handleCheck(row)
          }
        }
      }
    },
    uploadFile() {
      const _this = this
      // 获取file
      let file = this.uploadList[0]

      // 实例化
      let formData = new FormData()
      formData.append("name", "wiiiiiinney");
      formData.append('file', file.file)
      uploadFileApi(formData)
          .then(res => {
            const {code} = res
            if (code === 200) {
              // 上传成功，回到首页
              _this.showUploadDiv = false
              _this.filePath.length = 1;
            } else {
              Message.error('上传失败')
            }
          })
          .catch(err => {
            console.error(err)
          })
    },
    handleChange({fileList}) {
      this.fileListLength = fileList.length
    },
    async beforeUpload({file, fileList}) {
      if (fileList.length >= 1) {
        Message.error('只能上传一个文件')
        return false
      }
      if (file.file.type !== 'application/x-zip-compressed') {
        Message.error('只能上传zip格式的压缩文件，请重新上传')
        return false
      }
      return true
    }
  },
  watch: {
    filePath: {
      handler(val) {
        this.getFileList()
      },
      deep: true
    },
  },
}
</script>

<style lang="less" scoped>
@width: 1100px;
@height: 300px;

.container {
  .menuDiv {
    height: 48px;
    background-color: #34495e;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-right: 14px;

    .myButton {
      margin-left: 12px;
    }
  }

  .navigation {
    height: 22px;
    line-height: 22px;
    width: @width;
    background: bisque;
    margin: 8px auto;
    padding-left: 4px;
  }

  .fileListContainer {
    width: @width;
    margin: auto;
    min-height: @height;
    background-color: #ecf0f1;
    padding: 4px;
    border-radius: 4px;
  }

  tr {

    td, th {
      max-width: 100px;
    }
  }
}

:deep(.n-upload-dragger) {
  width: @width;
  height: @height;
  padding-top: 76px;
}

</style>
