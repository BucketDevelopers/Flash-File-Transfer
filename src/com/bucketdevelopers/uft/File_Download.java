package com.bucketdevelopers.uft;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.common.methods.MimeUtils;
import com.common.methods.XmlParser;
import com.ipaulpro.afilechooser.FileChooserActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class File_Download extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 100;
	Button upload;
	ListView listview;
	ArrayList<String> filearray;
	ArrayAdapter<String> arrayadapter;
	XmlParser xml;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_download);
		upload = (Button) findViewById(R.id.button1);
		listview = (ListView) findViewById(R.id.filelist);
		upload.setOnClickListener(this);
		filearray = new ArrayList<String>();
		xml = new XmlParser(getFilesDir());
		filearray = xml.fileList();

		arrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, filearray);
		listview.setAdapter(arrayadapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				final int pos = position;
				final CharSequence[] items = { "Open", "Remove from list",
						"Clear entire list" };
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						File_Download.this);
				dialog.setTitle(filearray.get(pos)).setItems(items,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int index) {
								// TODO Auto-generated method stub
								File tempfile;
								switch (index) {
								case 0: // Open that file
									String fpath = XmlParser.getFilePath(
											filearray.get(pos)).toLowerCase();
									String extension = fpath.substring(fpath
											.lastIndexOf('.') + 1);
									File file = new File(fpath.substring(1));
									if (MimeUtils
											.guessMimeTypeFromExtension(extension) == null)
										Toast.makeText(getBaseContext(),
												"Unknown File Type",
												Toast.LENGTH_SHORT).show();
									else {
										Intent intent = new Intent();
										intent.setAction(android.content.Intent.ACTION_VIEW);
										intent.setDataAndType(
												Uri.fromFile(file),
												MimeUtils
														.guessMimeTypeFromExtension(extension));
										startActivity(intent);
									}
									break;
								case 1: // Remove that file
									Map<String, String> filemap = XmlParser
											.getFileMap();
									Log.d("Hell",
											"List has " + filearray.size());
									/*
									 * for(int i= 0; i< filearray.size();i++) {
									 * 
									 * filemap.put(filearray.get(i),
									 * xml.getFilePath(filearray.get(i))); }
									 */
									// Log.d("Hell","Map is "+filemap.toString());
									filearray.remove(pos);
									arrayadapter.notifyDataSetChanged();
									tempfile = new File(getFilesDir(),
											"list.xml");
									tempfile.delete();
									XmlParser.checkXml(getFilesDir(),
											"list.xml");
									XmlParser t_xml = new XmlParser(
											getFilesDir());
									Log.d("Hell",
											"List has " + filearray.size());
									for (int i = 0; i < filearray.size(); i++) {
										// Log.d("attempt",
										// "Trying "+filearray.get(i)+" at "+filemap.get(filearray.get(i)));
										t_xml.addFile(filearray.get(i),
												filemap.get(filearray.get(i)));
									}
									Toast.makeText(getBaseContext(), "Removed",
											Toast.LENGTH_SHORT).show();
									break;
								case 2:
									tempfile = new File(getFilesDir(),
											"list.xml");
									tempfile.delete();
									XmlParser.checkXml(getFilesDir(),
											"list.xml");
									filearray.clear();
									arrayadapter.notifyDataSetChanged();
									break;
								}

							}

						});

				dialog.show();

			}
		});
	}

	public void onClick(View view) {
		Button clickedBtn = (Button) view;

		switch (clickedBtn.getId()) {
		case R.id.button1:
			// TODO Auto-generated method stub
			// Intent getContentIntent = FileUtils.createGetContentIntent();
			// Intent intent = Intent.createChooser(getContentIntent,
			// "Select a file");
			Intent intent = new Intent(this, FileChooserActivity.class);
			startActivityForResult(intent, REQUEST_CODE);
			break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				String tempFilePath, tempFileName;
				final Uri uri = data.getData();
				File file = FileUtils.getFile(getApplicationContext(),uri);
				tempFilePath = file.toString();
				tempFileName = tempFilePath.substring(tempFilePath
						.lastIndexOf('/') + 1);
				Log.d("msg1", tempFileName);
				XmlParser t_xml = new XmlParser(getFilesDir());
				t_xml.addFile(tempFileName, tempFilePath);
				Log.d("msg2", tempFilePath);
				filearray.add(tempFileName);
				arrayadapter.notifyDataSetChanged();
			}
		}
	}
	/*
	 * @Override protected void onResume() { super.onResume();
	 * 
	 * TextView textIpaddr = (TextView) findViewById(R.id.ipaddr);
	 * 
	 * String ipaddress = IpAddress.getHostIPAddress();
	 * textIpaddr.setText("Please access! http://" + ipaddress + ":" + PORT);
	 * 
	 * server = new MyHTTPD(); try { server.start(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * @Override protected void onPause() { super.onPause(); if (server != null)
	 * server.stop(); }
	 * 
	 * // Http Response Server
	 * 
	 * private class MyHTTPD extends NanoHTTPD { public MyHTTPD() { super(PORT);
	 * }
	 * 
	 * public Response serve(String uri, Method method, Map<String, String>
	 * header, Map<String, String> parms, Map<String, String> files) {
	 * 
	 * XmlParser xml = new XmlParser(getFilesDir()); //xml.addFile("abc.txt",
	 * "/sdcard/test/abc.txt"); ArrayList<String> fileList = xml.fileList();
	 * if(uri.contentEquals("/")){ StringBuilder filesHtml = new
	 * StringBuilder(); for(int i=0;i<fileList.size();i++) {
	 * filesHtml.append("<a href=\""
	 * +fileList.get(i)+"\">"+fileList.get(i)+"</a><br/>"); }
	 * 
	 * StringBuilder sb = new StringBuilder(); sb.append("<html>");
	 * sb.append("<head><title>Debug Server</title></head>");
	 * sb.append(filesHtml.toString()); sb.append("<body>");
	 * sb.append("</body>"); sb.append("</html>"); return new
	 * Response(sb.toString()); }
	 * 
	 * // File path = Environment.getExternalStorageDirectory(); else{ String
	 * fileName = uri.substring(1); String fpath=xml.getFilePath(fileName); File
	 * file = new File(fpath); //int ch; //StringBuilder text = new
	 * StringBuilder();
	 * 
	 * try { //BufferedReader br = new BufferedReader(new FileReader(file));
	 * FileInputStream in = new FileInputStream(file); // String line; // while
	 * ((ch = in.read()) != -1) { // text.append((char)ch); //text.append('\n');
	 * // }
	 * 
	 * Response res= new Response(Status.OK,"application/octet-stream",in );
	 * res.addHeader("Content-Disposition",
	 * "attachment; filename=\""+fileName+"\""); return res; } catch
	 * (IOException e) { //You'll need to add proper error handling here }
	 * return new Response("Fail!!");
	 * 
	 * } }//end of serve } //end of class myHTTPD
	 */
}// end of main activity
