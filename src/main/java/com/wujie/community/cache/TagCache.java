package com.wujie.community.cache;

import com.wujie.community.dto.TagCacheDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {

    public static List<TagCacheDto> get(){
        //创建一个存放类别与标签的集合
        ArrayList<TagCacheDto> tagDTOS = new ArrayList<>();
        //创建开发语言对象
        TagCacheDto program = new TagCacheDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c"
                ,"golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net"
                ,"lua","scala","coffeescript","actionscript","rust ","erlang ","perl"));
        tagDTOS.add(program);

        //创建平台框架对象
        TagCacheDto framework = new TagCacheDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel","spring","express","django","flask ","yii",
                "ruby-on-rails","tornado","koa","struts"));
        tagDTOS.add(framework);

        //创建服务器对象

        TagCacheDto server = new TagCacheDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker ","apache","ubuntu","centos","缓存",
                "tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        //创建数据库缓存对象
        TagCacheDto db = new TagCacheDto();
        db.setCategoryName("数据库和缓存");
        db.setTags(Arrays.asList( "mysql","redis","mongodb","sql","oracle","nosql"
                ,"memcached","sqlserver","postgresql","sqlite"));
        tagDTOS.add(db);

        //创建开发工具对象
        TagCacheDto tool = new TagCacheDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","visual-studio-code","vim",
                "sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atom","emacs","textmate","hg"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    //校验我们的tags
    public static String filterInvalid(String tags){
        //先拿到我们从客户端传递过来的每一个tags
        String[] split = StringUtils.split(tags, ",");
        //拿到所有的tags
        List<TagCacheDto> tagCacheDtos = get();
        //进行二维数组的遍历获取category和tags
        List<String> tagList = tagCacheDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //判断客户端的标签是否在tags里面有包含
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
