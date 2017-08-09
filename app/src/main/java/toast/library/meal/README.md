#Android Meal Library with Mir(whdghks913)
##VERSION 6 (UPDATE 20150225)

***

###사용하는 라이브러리*
* jericho-html-3.3.jar



###사용 방법 안내
static으로 선언하여 어디서든 바로 사용가능합니다
MealLibrary.(사용할 메소드 이름)으로 급식을 가져올수 있으며 자세한 사용법은 아래에 있습니다
AsyncTask를 사용하여 라이브러리를 사용해 주세요, 어떻게 쓰는지 모르시면 원당고 앱을 참고해 주세요
개발자는 인터넷이 연결되지 않았을때 라이브러리를 호출하지 않도록 코드를 구성해야 합니다



###업데이트 안내
- 나이스 홈페이지 구조 변경에 따라 새로운 파싱 방법 사용
- getDateNew(), getKcalNew(), getMealNew(), getPeopleNew() 사용가능
- 기존 메소드인 getDate(), getKcal(), getMeal(), getMonthMeal(), getPeople()은 삭제됨
- getPeopleNew()에서 year, month, day가 기존 getPeople()방식을 사용하던 코드 실수 수정...(죄송해요..;)
- VERSION 6부터는 New()가 붙지 않은 메소드는 사용할수 없습니다.



###오픈 소스 안내
- 원본 소스 : http://blog.naver.com/rimal
- ITcraft's Github Project의 오픈소스 : https://github.com/mhkim4886/OkdongMidSch/blob/master/src/toast/library/meal/MealLibrary.java
- 원본 라이센스 : Public Open Library
- 수정 : 2014-03-16 ~
- 업로드 : https://bitbucket.org/whdghks913/wondanghighschool (src/toast/library/meal/MealLibrary.java)

***

###급식 다운로드를 위한 AsyncTask를 지원합니다
원당고 학교앱이 업데이트됨에따라 급식을 가져오는 방법이 변경되었습니다
아래 방법을 따라하시면 업데이트된 방식을 적용하실수 있습니다

* wondang/icehs/kr/whdghks913/wondanghighschool/bap/ProcessTask.java, wondang/icehs/kr/whdghks913/wondanghighschool/tool/BapTool.java, wondang/icehs/kr/whdghks913/wondanghighschool/tool/Preference.java를 가져옵니다
ProcessTask.java는 급식 파싱 라이브러리를 사용하는 AsyncTask이며 BapTool.java와 Preference.java를 이용해 급식을 저장합니다

* ProcessTask.java를 열고 자신의 학교에 맞게 정보를 수정하세요

* 급식을 다운받는 액티비티(서비스등)에서 ProcessTask를 상속받는 class를 만들어줘야 합니다

```java
BapDownloadTask mProcessTask;

public class BapDownloadTask extends ProcessTask {
    public BapDownloadTask(Context mContext) {
        super(mContext);
    }

    @Override
    public void onPreDownload() {
	    // 다운로드 전에 해야하는 코드를 여기에 작성하세요
    }

    @Override
    public void onUpdate(int progress) {
	    // 진행상황을 표시할수 있는 코드를 여기에 작성하세요
    }

    @Override
    public void onFinish(long result) {
	    // 급식을 가져오는 코드를 여기에 작성하세요
    }
}
```

* 만든 class를 실행해주세요

```java
mProcessTask = new BapDownloadTask(this);
mProcessTask.execute(year, month, day);
```

ProcessTask를 execute할때 3가지 값을 순서대로 전달해주어야 합니다
int형식이며 Calendar에서 얻은 값을 그대로 넣어야 합니다

Calendar에서 얻은 month값은 1월=0, 12월=11입니다
이를 ProcessTask에서 잡아주므로 따로 month+1을 하지 마세요


* ProcessTask를 실행하면 BapTool을 이용해 급식을 저장합니다 (BapTool.saveBapData)
급식을 가져올때는 마찬가지로 BapTool을 사용해서 가져옵니다

```java
BapTool.restoreBapDateClass mData = BapTool.restoreBapData(Context, year, month, day);
```
하루의 급식 정보가 아래 class에 담겨 반환되며, mData를 이용해 급식을 가져올수 있습니다

```java
public static class restoreBapDateClass {
    public String Calender;
    public String DayOfTheWeek;
    public String Lunch;
    public String Dinner;
    public boolean isBlankDay = false;
}

mData.Calender, mData.DayOfTheWeek, mData.Lunch, mData.Dinner, mData.isBlankDay
```


* isBlankDay가 중요합니다
isBlankDay가 true일경우 데이터가 저장되지 않은 상태입니다
이때 ProcessTask를 실행해서 데이터를 받아옵니다

```java
if (mData.isBlankDay) {
    // ProcessTask 실행
}
```


* 자세한 정보는 이 프로젝트의 BapActivity.java의 getBapList()를 참고하세요

***

##How To Use?


###Deprecated API
####MealLibrary.getDate()
```java
MealLibrary.getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
MealLibrary.getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
MealLibrary.getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)
```
일주일치 날짜를 반환합니다
schYmd 또는 year, month, day를 입력하지 않을경우, 현재 서버 날짜를 기준으로 급식을 가져옵니다
schYmd 또는 year, month, day를 입력해서 원하는 날짜의 한주 급식을 가져올수도 있습니다
원당고 앱에서는 year, month, day를 이용해서 지난주, 다음주, 날짜를 선택해서 급식을 가져올수 있습니다
String[]에서 [0]에는 일요일의 정보가, [6]에는 토요일의 정보가 담기며, 총 index 길이는 0~6 입니다



####MealLibrary.getMeal()
```java
MealLibrary.getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
MealLibrary.getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
MealLibrary.getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)
```
일주일치 급식을 반환합니다
급식이 없을경우 " " 또는 "" 또는 null으로 반환하며, 자세한 설명은 getDate()와 같습니다




####MealLibrary.getMonthMeal()
```java
MealLibrary.getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYm)
MealLibrary.getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month)
```
한달 급식을 반환합니다
getMeal()에서 사용하는 schYm 파라메터와 getMonthMeal()에서 사용하는 schYm파라메터는 다른 형식을 사용해야 합니다 (아래 참조)
String[]의 길이는 한달 날짜 길이와 같으며, 2월은 윤년을 위해 길이가 29입니다



####MealLibrary.getKcal()
```java
MealLibrary.getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
MealLibrary.getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
MealLibrary.getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)
```
일주일치 급식의 칼로리를 가져옵니다
위와 같습니다



####MealLibrary.getPeople()
```java
MealLibrary.getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
MealLibrary.getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
MealLibrary.getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)
```
일주일치 급식 인원을 반환합니다
위와 같습니다



###New API
####MealLibrary.getDateNew()
-----------------------
```java
MealLibrary.getDateNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode)
MealLibrary.getDateNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode, year, month, day)
```



####MealLibrary.getKcalNew()
```java
MealLibrary.getKcalNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode)
MealLibrary.getKcalNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode, year, month, day)
```



####MealLibrary.getMealNew()
```java
MealLibrary.getMealNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode)
MealLibrary.getMealNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode, year, month, day)
```



####MealLibrary.getPeopleNew()
```java
MealLibrary.getPeopleNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode)
MealLibrary.getPeopleNew(CountryCode, schulCode, schulCrseScCode, schulKndScCode, schMmealScCode, year, month, day)
```

***

###변수 설명

####CountryCode
* 학교 교육청 코드, nice홈페이지 도메인과 같습니다
* EX) 인천 : ice.go.kr


####schulCode
* 학교 고유 나이스 코드
* EX) 인천의 학교 코드 검색 : http://hes.ice.go.kr/sts_sci_si00_001.do (E10000xxxx)
* 참조 : 중학교 나이스 코드 검색 : http://me2.do/xAY6Zij1
* 고등학교 나이스 코드 검색 : http://me2.do/G22fDh8l


####schulCrseScCode
* 학교 분류
* "1" : 병설유치원
* "2" : 초등학교
* "3" : 중학교
* "4" : 고등학교
* 분류번호랑 종류랑 안맞으면 반환이 안됩니다


####schulKndScCode
* 학교 종류
* "01" : 유치원
* "02" : 초등학교
* "03" : 중학교
* "04" : 고등학교


####schMmealScCode
* 반환할 식사 값
* 조식 : "1"
* 중식 : "2"
* 석식 : "3"


####schYmd
* 원하는 날짜의 급식 정보를 얻기 위해 필요합니다
* String 형식 : 년.월.일
* EX) "2014.03.16"
* 새로운 New메소드에서는 schYmd을 지원하지 않고 year, month, day만 지원합니다


####schYm
* 원하는 달의 급식 정보를 얻기 위해 필요합니다
* String 형식 : 년.월
* EX) "2014.03"
* 새로운 New메소드에서는 schYm 지원하지 않고 year, month, day만 지원합니다


####year, month, day
* schYmd와 schYm의 정보를 세분화 해서 각각 정보를 넘겨줄때 사용합니다
* EX) year = "2014", month = "03", day = "16"