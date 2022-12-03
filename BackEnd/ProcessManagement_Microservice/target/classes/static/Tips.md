# Tips

Idea 的操作十分方便，比如会自动 import，显示可能项等，这需要大家自己在 操作过程中熟悉





# 创建项目+mongodb 数据库的连接

以上操作全部按照之前的文档执行，此处不再赘述





# Model层创建

### 建立package与class

在 com.example.XXXmicroservices(项目名称)的目录下新建 package，包的名即为 model(其他 层亦然)，在其中添加 java 类

![layer](C:\Users\JAMES\Desktop\layer.png)

注意，此层的 java 类选择普通类即可，命名为子系统的名称，比如 User、Project;

![select](C:\Users\JAMES\Desktop\select.png)



### import

~~~
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
~~~



### 构造model

~~~
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("Project")
public class Project implements Serializable{
    @Id
    private String id;
    private String projectName;
    private String organization;
    private String describe;
    private String[] followerList;
}
~~~

其中@Document中的字符串为类名，比如project，user等；

主码(本例中为id)前加上@Id注释，系统会在我们未对主码赋值时进行自增操作；





# Dao层创建

### 建立package与class

同 model 层——需要注意，此处建立 java 类时，需选择 interface

![interface](C:\Users\JAMES\Desktop\interface.png)



### import

~~~
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.projectmicroservices.model.Project;
import org.springframework.stereotype.Repository;

import java.util.Optional;
~~~



### 构造dao

~~~
@Repository
public interface ProjectDao extends MongoRepository<Project, String> {
    public Optional<Project> findById(String id);
}
~~~

注意加上 Repository 注释；

此处 Optional 会在后文讲述；

此处 extends from MongoRepository， 提供了大量基础的数据库操作，我们只需要实现我们特定的，库中没有的函数即可；





# Service层创建

### 建立package与class

创建class时选择普通java类即可；



### import

~~~
import com.example.projectmicroservices.model.Project;
import com.example.projectmicroservices.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
~~~

注意其中有部分类未用到，但后期开发可能用到，建议加上；



### 构造service

~~~
@Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    public int isExist(String id){
        Project checkProject=new Project();
        checkProject.setId(id);
        Example<Project> projectExample=Example.of(checkProject);
        long count = projectDao.count(projectExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public void addProject(Project project){
        int exist=this.isExist(project.getId());
        if(exist!=1){
            projectDao.save(project);
            System.out.println("添加成功！");
        }
        else{
            System.out.println("该项目已存在，无法再次添加！");
        }
    }

    public List findAllProject(){
        List<Project> projectList = projectDao.findAll();
        return projectList;
    }

    public Optional<Project> findProjectById(String id){
        Optional<Project> project=null;

        Project checkProject3=new Project();
        checkProject3.setId(id);
        Example<Project> projectExample=Example.of(checkProject3);
        long count2 = projectDao.count(projectExample);

        if((int)count2!=0){
            project = projectDao.findById(id);
            System.out.println("找到用户！");
        }
        else{
            System.out.println("该用户不存在");
        }
        System.out.println(count2);
        return project;
    }

    public String[] getFollowList(String id){
        Optional<Project> project=this.findProjectById(id);
        return project.get().getFollowerList();
    }

    public int changeProjectInfo(Project newProject){
        int exist=this.isExist(newProject.getId());

        if(exist==0){
            System.out.println("该项目不存在");
            return 0;
        }

        Optional<Project> OldUser=this.findProjectById(newProject.getId());

        int legal=1;
        if(newProject.getProjectName().length()>15||
                newProject.getProjectName().length()==0||
                newProject.getProjectName().contains(" ")||
                newProject.getDescribe().length()>200
        ){
            legal=0;
        }

        if(legal==1){
            projectDao.save(newProject);
            System.out.println("修改成功！");
        }
        else{
            System.out.println("修改失败！");
        }
        return legal;
    }

    public void deleteProject(String id){
        projectDao.deleteById(id);
    }
}
~~~

加上 Service 注释；

加上 autowired 注释；

Service 层调用 dao 层，故 private 一个 XXXdao；



### 自定义查询写法

~~~
Project checkProject=new Project();
        checkProject.setId(id);
        Example<Project> projectExample=Example.of(checkProject);
        long count = projectDao.count(projectExample);
~~~

其写法具体为： new 一个对象，set 相关属性，example，count——就可以得到满足条件的数据对象， 还可统计其数量，这一点我是用来处理Optional中返回值始终不为空，无法判断某一数据对象是否存在的问题，使用count，就可以解决；





# Controller层创建

### 建立package与class



### import

```
import com.example.projectmicroservices.model.Project;
import com.example.projectmicroservices.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
```



### 构造controller

~~~
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    //good
    @GetMapping("/add")
    public void addProject(Project newProject){
        projectService.addProject(newProject);
    }

    //good
    @GetMapping("/change")
    public int changeProjectInfo(Project newProject){
        return projectService.changeProjectInfo(newProject);
    }

    //good
    @GetMapping("/find")
    public Optional<Project> findProjectById(String id){
        return projectService.findProjectById(id);
    }

    //goood
    @GetMapping("/all")
    public List findAllProject(){
        return projectService.findAllProject();
    }


    //good
    @GetMapping("/delete")
    public void deleteProject(String id){
        projectService.deleteProject(id);
    }

}
~~~



~~~
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
~~~

加上 RestController注释；

加上Autowired 注释；

Controller 调 service，故 private 一个 XXXservice对象；



~~~
@GetMapping("/add")
~~~

这里暂时用 getmapping 进行测试，可修改；

括号内为 url，默认本地，测试时在 localhost 之后复粘即可；

~~~
http://localhost:8080/add
~~~





# 测试

连接 mongoDB 数据库(推荐 compass)， 左键运行项目；

![test](C:\Users\JAMES\Desktop\test.png)

打开测试工具(我用的 apipost) ，即可进行接口测试；

![api](C:\Users\JAMES\Desktop\api.png)

