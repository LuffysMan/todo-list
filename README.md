# TODO List
- 一个任务规划应用程序, 它可以添加任务, 查看添加后的任务, 修改已添加的任务.
- 每个任务有开始时间, 结束时间, 完成进度;
- 每个任务可以有若干子任务, 完成进度取决于子任务的完成进度; 
- 可以修改任务名称, 任务时间, 任务描述, 所指向的父任务;
- 可以删除任务;
- 添加的任务应当持久化到磁盘;

## 1. 工程结构

```
src
 |- main
 |   |- java <---  代码
 |   |- resources <--- 资源文件
 |
 |- test <--- 单元测试
 |
 |- build.gradle  <--- gradle工程文件
```
程序入口在 *Program.java*文件中. 

1. 用户在终端输入命令
1. 命令作为参数传入 `CommandExecutor.execute` 方法
1. `CommandExecutor` 通过命令找到合适的 `CommandHandler`
1. `CommandHandler` 调用 `handle` 方法产生 `CommandResponse`
1. 将 `CommandResponse` 中 `getMessage` 方法返回的信息输出
1. 输出信息显示在命令行中。

## Story1: 添加任务
- 该应用可以添加多个任务, 一个任务可以有一个父任务和多个子任务, 任务之间的关系可以用一棵树来表示, 每个任务作为树的一个节点; 每个任务可以在数据库中用一条记录来表示: 

    | id | name | begin | end | parent | children | state | desc |
    | -- | -- | -- | -- | -- | -- | -- | -- |
    | 1  | life | 1992-1-10 | NULL | NULL | NULL | active | life is the task through your life |

    每条任务记录有8个字段, 分别是id, name, begin, end, parent, children, state, desc  

    - id: 任务id, 唯一标识, 32位整数.
    - name: 任务名称, 不能为NULL, 不能超过32个字符.
    - begin: 任务开始时间, 不能为NULL.
    - end: 任务结束时间, 不能为NULL.
    - parent: 父任务ID(默认为1).
    - children: 子任务ID(如果有任务以当前任务为父任务, 则添加到末尾, 以逗号分割), text类型.
    - state: 任务状态, 默认为0, boolean类型, 不能为NULL.
    - desc: 任务描述, text类型;

- 命令: ```todo init```  
    - 应当能在当前文件夹新建sqlite数据库文件 my.todo, 并且能在数据库文件中新建一个数据库todo, 在数据库todo中新建任务表格tasks;
    - 在解析命令时, 首尾的空格会被去掉: 例如```  todo init  ``` 等价于 ```todo init```.
    - 分割命令的多余空格会被去除: 例如```todo   init``` 等价于```todo init```.
    - 不符合规则的命令, 比如```todo init hello```, 应当输出: ```Bad Command: todo <init>```


- 命令: ```todo add <name> <begin> <end> [--parent=<parent>] [--state=<state>] [--desc=<description>]```;  
    - 添加任务, 必须指定任务名称, 任务开始时间, 任务结束时间; 父任务ID和描述可选;
    - 如果```todo add``` 后接的参数少于3必选参数 ```name begin end```, 则任务添加失败, 输出```Bad Command: todo add <name> <begin> <end> [--parent=<parentId>] [--state=<state>] [--desc=<description>]```;
    - 如果提供的name长度超过32个字符, 则任务添加失败, 输出```Bad Command: name too long```;
    - 如果 ```begin``` 或 ```end``` 的格式不符合日期的格式 ```yyyy-mm-dd```, 则任务添加失败, 输出: ```Bad Command: invalid date format(yyyy-mm-dd)```;
    - 如果 ```parent``` 不存在, 则任务添加失败, 输出: ```Bad Command: parent not found```;

## Story2: 查询任务
该应用可以使用指令来查询整个任务树, 可以指定任务树的最大深度(列表是树形结构), 也可以查询以某个任务名称为根节点的任务树;

- ```todo list [--max-depth=2]```: 查询整个任务树, 如果指定了max-depth, 则查询指定的层数
- ```todo list <name> [--max-depth=<depth>]```: 查询以指定名称任务为根节点的任务树, 可以指定查询深度

## Story3: 修改任务
该应用可以修改指定任务节点除id字段外的任意字段信息; 需要注意的是, 当修改了任务节点的parent时, 对应的parent任务节点的children字段也应当被同步修改;
- 命令: ```todo set <id> [--name=<name> --begin=<begin> --end=<end> --parent=<parent> --children=<children> --state=<state> --desc=<desc>]```

## Story4: 删除任务
该应用可以删除任务节点, 以任务节点为根的子树将被整个删除;
- 命令: ```todo drop <id>```