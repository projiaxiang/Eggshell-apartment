<template>
  <div v-if="entities.length === 0 && !isAdd">
    <div style="margin-left: 250px" v-if="entities.length === 0 && !isAdd">
      <span style="font-size: 20px">未发布任何房源</span>
      <el-button style="font-size: 20px" type="text" @click="addHouse">去发布</el-button>
    </div>
  </div>
  <div v-else-if="entities.length !== 0 && !isAdd && !entity.id">
    <el-button type="primary" style="margin-left: 88%" @click="addHouse">继续发布</el-button>
    <div @click="showHouse(entity)" v-for="(entity, index) in entities" :key="index" class="div-top" style="cursor: pointer;">
      <el-card shadow="hover">
        <el-col :span="6">
          <img v-if="entity.picture && entity.picture.length" width="110px" height="110px" :src="baseUrl + entity.picture[0].name"/>
          <img v-else width="110px" height="110px" :src="baseUrl + '1.jpg'"/>
        </el-col>
        <el-col :span="12">
          <span style="display: block;font-size: 20px">{{entity.title}}</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">{{toApartment(entity.apartment)}}  {{entity.size}}㎡  {{entity.rent == '1' ? '合租' : '整租'}}</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">{{entity.subway}}</span>
        </el-col>
        <el-col :span="6">
          <div style="float: right">
            <span style="display: block">{{$moment(entity.createTime).format('YYYY-MM-DD HH:mm:ss')}}</span>
            <span style="display: block;margin-left: 34.5px;margin-top: 10px"><span style="color: red;font-size: 25px">{{entity.money}}</span>元/月</span>
            <el-button type="text" style="float: right" @click="delHouse(entity.id)">删除</el-button>
          </div>
        </el-col>
      </el-card>
    </div>
    <div class="block" style="float: right">
      <el-pagination
        style="margin: 0 auto"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage1"
        :page-size="pageSize"
        background
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
  <div v-else>
    <el-form label-width="90px" style="width: 74.4%">
      <el-form-item label="标题">
        <el-input v-model="entity.title"></el-input>
      </el-form-item>
      <el-form-item label="出租方式">
        <el-radio-group v-model="entity.rent">
          <el-radio label="1">合租</el-radio>
          <el-radio label="2">整租</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="取暖方式">
        <el-radio-group v-model="entity.heating">
          <el-radio label="1">集中供暖</el-radio>
          <el-radio label="2">自采暖</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="已租赁年限">
        <el-radio-group v-model="entity.isFirst">
          <el-radio label="1">首次出租</el-radio>
          <el-radio label="2">非首次出租</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="89px">
      <el-form-item label="户型">
        <el-select v-model="entity.apartment" placeholder="请选择户名">
          <el-option label="一室一厅" value="yi"></el-option>
          <el-option label="两室一厅" value="er"></el-option>
          <el-option label="三室一厅" value="san"></el-option>
          <el-option label="四室一厅" value="si"></el-option>
          <el-option label="五室一厅" value="wu"></el-option>
          <el-option label="六室一厅" value="liu"></el-option>
          <el-option label="七室一厅" value="qi"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="建筑面积">
        <el-input v-model="entity.size" :rules="[{ type: 'number', message: '年龄必须为数字值'}]"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="89px">
      <el-form-item label="朝向">
        <el-select v-model="entity.direction" placeholder="请选择朝向">
          <el-option label="东" value="east"></el-option>
          <el-option label="南" value="south"></el-option>
          <el-option label="西" value="west"></el-option>
          <el-option label="北" value="north"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="楼层">
        <el-input v-model="entity.floor"></el-input>
      </el-form-item>
    </el-form>
    <el-form :inline="true" label-width="89px">
      <el-form-item label="区域">
        <el-cascader
          placeholder="可根据名称筛选"
          :options="options"
          filterable
          clearable
          v-model="selectLocation"
          change-on-select/>
      </el-form-item>
      <el-form-item label="租金">
        <el-input-number size="medium" v-model="entity.money"></el-input-number>
      </el-form-item>
    </el-form>
    <el-form label-width="90px" style="width: 74.4%">
      <el-form-item label="地铁">
        <el-input v-model="entity.subway"></el-input>
      </el-form-item>
      <el-form-item label="房屋简介">
        <el-input type="textarea" :rows="3" v-model="entity.introduction"></el-input>
      </el-form-item>
      <el-form-item label="房屋配置">
        <el-tag>床</el-tag>
        <el-tag style="margin-left: 10px">WIFI</el-tag>
        <el-tag style="margin-left: 10px">洗衣机</el-tag>
        <el-tag style="margin-left: 10px">空调</el-tag>
        <el-tag style="margin-left: 10px">智能锁</el-tag>
      </el-form-item>
    </el-form>
    <el-form label-width="90px" style="width: 74.4%">
      <el-form-item label="上传照片">
        <img style="padding-left: 5px" v-for="item in entity.picture" width="28%" height="28%" :src="baseUrl + item.name"/>
        <el-upload
          action="http://127.0.0.1:9091/upload"
          name="picture"
          list-type="picture-card"
          :on-remove="handleRemove"
          :before-upload="beforeAvatarUpload"
          :on-success="afterSavePicture">
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-button type="primary" style="margin-left: 4%" @click="saveOrUpdate">保存</el-button>
      <el-button type="primary" plain @click="backPage">取消</el-button>
    </el-form>
  </div>
</template>

<script>
  import location from '../../config/China_Province_City.json'
  import UserRedis from '../../utils/RedisUtil.js'
  import HouseUtil from '../../utils/HouseUtil.js'

  export default {
    mixins: [UserRedis, HouseUtil],
    data() {
      return {
        total: 0,
        currentPage1: 1,
        pageSize: 4,
        options: location,
        entities: [],
        entity: {
          id: null,
          title: null,
          heating: null,
          isFirst: null,
          size: null,
          apartment: null,
          subway: null,
          direction: null,
          floor: null,
          money: null,
          introduction: null,
          createTime: null,
          configure: [],
          rent: null,
          province: null,
          city: null,
          county: null,
          picture: [],
          userId: null
        },
        isAdd: false,
        dialogImageUrl: '',
        baseUrl: 'http://127.0.0.1:9091/image/',
        dialogVisible: false,
        pIds: '',
        selectLocation: [],
        user: {
          id: null,
          username: null
        }
      }
    },
    created() {
      this.init()
    },
    mounted() {

    },
    methods: {
      async init() {
        let key = localStorage.getItem('user_session')
        await this.getUserInfoFromRedis(key)
        this.loadHouse()
      },
      loadHouse() {
        let self = this
        let startPage = (this.currentPage1 - 1)*this.pageSize

        this.$axios({
          method: "post",
          url: "/find/house",
          data: this.$qs.stringify({
            startPage: startPage,
            pageSize: self.pageSize,
            userId: self.user.id
          })
        }).then((res)=>{
          self.entities = res.data.house
          self.total = res.data.total
        })
      },
      addHouse() {
        this.isAdd = true
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      afterSavePicture(response, file, fileList) {
        this.pIds += response.message + ','
      },
      async saveOrUpdate() {
        this.entity.userId = this.user.id
        await this.splitLocation()
        if (this.entity.id) {
          this.updateHouse()
        } else {
          this.saveHouse()
        }
      },
      saveHouse() {
        delete this.entity['picture']
        let self = this

        this.$axios({
          method: "post",
          url: "/add/house",
          data: this.entity,
          params: {
            pIds: this.pIds
          },
        }).then((res)=>{
          if (res.data.success) {
            self.$message({message: '房屋添加成功', type: 'success'})
            self.isAdd = false
            self.loadHouse()
            self.cleanEntity()
          } else {
            self.$message.error('房屋添加失败')
          }
        })
      },
      updateHouse() {
        delete this.entity['picture']
        let self = this

        this.$axios({
          method: "post",
          url: "/update/house",
          data: this.entity,
          params: {
            pIds: this.pIds
          },
        }).then((res)=>{
          if (res.data.success) {
            self.$message({message: '房屋更新成功', type: 'success'})
            self.isAdd = false
            self.loadHouse()
            self.cleanEntity()
          } else {
            self.$message.error('房屋更新失败')
          }
        })
      },
      delHouse(id) {
        let self = this
        let e = window.event
        e.stopPropagation()
        this.$confirm('此操作将永久删除该房源, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$axios({
            method: "post",
            url: "/delete/house",
            data: this.$qs.stringify({
              id: id
            }),
          }).then((res)=>{
            if (res.data.success) {
              self.$message({message: '删除成功', type: 'success'})
              self.loadHouse()
            } else {
              self.$message.error('删除失败')
            }
          })
        }).catch(() => {

        })
      },
      showHouse(entity) {
        this.isAdd = true
        this.entity = entity
        this.selectLocation = Array.from({
          0: this.entity.province,
          1: this.entity.city,
          2: this.entity.county,
          length: 3
        })
      },
      splitLocation() {
        let selectLocation = this.selectLocation
        if (selectLocation.length === 1) {
          this.entity.province = selectLocation[0]
        } else if (selectLocation.length === 2) {
          this.entity.province = selectLocation[0]
          this.entity.city = selectLocation[1]
        } else if (selectLocation.length === 3) {
          this.entity.province = selectLocation[0]
          this.entity.city = selectLocation[1]
          this.entity.county = selectLocation[2]
        }
      },
      backPage() {
        this.isAdd = false
        this.cleanEntity()
      },
      cleanEntity() {
        this.entity = {
          id: null,
          title: null,
          heating: null,
          isFirst: null,
          size: null,
          apartment: null,
          subway: null,
          direction: null,
          floor: null,
          money: null,
          introduction: null,
          createTime: null,
          configure: [],
          rent: null,
          province: null,
          city: null,
          county: null,
          userId: null
        }
      },
      handleCurrentChange() {
        this.loadHouse()
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .div-top {
    margin-top: 5px;
    padding-bottom: 10px;
  }
  .div-top-0 {
    margin-top: 0px;
  }
</style>
