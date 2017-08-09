package dongbaek.hs.kr.dbapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ViewHolder {
    public TextView mDate;
    public TextView mInfo;
}

class DateData{

    String date;
    String info;
    public DateData(String date,String info){
        this.date =date;
        this.info = info;

    }

    String getDate(){
        return date;
    }
    String getInfo(){
        return info;
    }
}

class DateAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<DateData> mListData = new ArrayList<DateData>();
    public DateAdapter(Context mContext){
        super();
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null ) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //inflating
            convertView = inflater.inflate(R.layout.itemstyle, null);

            holder.mDate = (TextView) convertView.findViewById(R.id.item_date);
            holder.mInfo = (TextView) convertView.findViewById(R.id.item_writer);
            convertView.setTag(holder);
        }

           else {
            holder = (ViewHolder) convertView.getTag();
        }



        DateData mData = mListData.get(position); // 등록했던 Item 을 지니는 ArrayList 에서 뷰가 요청하는 position 에서 데이터를 가져옵니다.

        holder.mDate.setText(mData.getDate());  // 각뷰에서 ArrayList 에 등록한 값을 가져와 setText로 텍스트를 지정합니다.
        holder.mInfo.setText(mData.getInfo());

        return convertView; //뷰 리턴
    }

    public void add(String mDate, String mInfo){
        DateData addInfo = null;
        addInfo = new DateData(mDate,mInfo);

        mListData.add(addInfo);

    }

}
