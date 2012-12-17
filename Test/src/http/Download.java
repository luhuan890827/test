package http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class Download {
	public static void main(String[] args) throws Exception {

		String sUrl = "http://nyan.moe.fm/8/f/30/0932f8d6bdd6a39faa9cae7902be24ac.mp3";
		int fLength = getcontentlength(sUrl);
		int start = 0;
		int nRead = 0;
		FileOutputStream fs = new FileOutputStream("c:/test.mp3");
		URL url = new URL(sUrl);
		// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setRequestProperty("range", "bytes=" + start + "-");
		// conn.setRequestProperty("User-Agent", "Internet Explorer");
		// InputStream is = conn.getInputStream();
		byte[] b = new byte[1024 * 4];

		BasicHttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, 3000);
		HttpClient client = new DefaultHttpClient(params);
		while (/* (nRead = is.read(b, 0, 1024)) > 0 && */start < fLength) {

			// HttpURLConnection conn = (HttpURLConnection)
			// url.openConnection();
			// conn.setRequestProperty("User-Agent", "Internet Explorer");
			// conn.setRequestProperty("range", "bytes=" + start + "-");
			// InputStream is = conn.getInputStream();
			HttpGet get = new HttpGet(sUrl);
			get.setHeader("range", "bytes=" + start + "-" + fLength);
			HttpResponse resp = null;
			HttpEntity entity = null;
			InputStream is = null;
			try {
				resp = client.execute(get);
				entity = resp.getEntity();
				is = entity.getContent();
				while (is != null && (nRead = is.read(b, 0, 1024 * 4)) > 0) {

					fs.write(b, 0, nRead);

					start += nRead;
					System.out
							.println("start=" + start + ":fLength=" + fLength);
				}
			} catch (Exception e) {
				continue;
			}

			// System.out.println(nRead+":"+fLength);

			// conn.disconnect();

		}

		fs.close();
		//

	}

	public static int getcontentlength(String str)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(str);
		HttpResponse resp = client.execute(get);
		return (int) resp.getEntity().getContentLength();
	}
}
