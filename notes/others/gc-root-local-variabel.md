
public class School {
  // instance variable
  private List<Grade> gradesList = new ArrayList<>();
  private List<Building> buildingsList = new ArrayList<>();

  public static void main(String[] args) {
    // school 是 local variable
    School school = new School();
    initSchoolGrades(school);

    // 初始化学校的建筑物list对象
    for (int i = 0; i < 100; i++) {
      Building building = new Building("Building - " + i);
      scroll.buildingsList.add(building);
    }
  }


  class Grade {
    public List<Student> studentsList;
    public String name;
    public Grade (String name) { this.name = name; }
  }

  private static void initScrollGrades(School school) {
    // 传入参数 school 不是 local variable
    // grade1/ studentsList 是 local variables
    Grade grade1 = new Grade("Grade 1");
    List<Student> studentsList = new ArrayList<>();
    for (int i = 0; i < 10,000; i++) {
      // student is also a local variable
      Student student = new Student("Student - " + i);
      studentsList.add(student);
    }
    grade1.studentsList = studentsList;
  }
}

上述代码中, 执行入口是 main 方法. 
先创建了一个scroll对象，然后调用了 initScrollGrades 方法
在这个方法中，创建了一个 grade1 对象，和1个studentsList以及1万个Student对象
他们之间的引用链关系是这样的：
grade1 -> studentsList -> (10,000) student

但注意这个地方：我故意没有把 grade1 加入到 school.gradesList 中，
这样的话 school 就不持有对 gradel1 的任何直接或间接的引用。

然后initSchoolGrades方法结束之后的代码中
创建了一个school对象和 100个Building对象
他们之间的引用链关系是通过buildingsList对象建立的：
school -> buildingsList -> (100) building

所以我们认为 GC Roots 有：
1. main 方法中的 scroll
2. initSchoolGrades 方法中的 grade1

如果我们假设JVM的内存只够容纳 1万个 students 对象 + 10个building对象
那么在执行对循环创建100个building对象的时候就会发生GC，
我们假设他发生在尝试创建第11个building对象的时候。
GC可以回收哪些对象呢？ 
就是grade1的对象引用链上的所有对象。
但是，在前10次循环中所创建的building对象已经被school引用了，所以他们是不能被GC的。

也就是说1万个students对象是可以被GC的，而这部分内存可以用来存放 school.buildingsList中的buildings。

换句话说，如果上面我提到的 “我故意没有让scroll跟grade1发生关系” 不存在的话（也就是他们建立了引用关系），那么这1万个students对象是不能被GC的，那么在创建第11个building对象的时候就OOM了

以上，是我对为什么local variable是GC root的相关思考。因为之前我就有疑问，这些(Threads / static / JNI reference) GC roots是怎么跟我创建的对象之间发生引用关系的呢？我就理解不了，直到看到local variable也是一种GC Root才感觉稍微理解了一点点。
个人理解 欢迎讨论哈。




