package de.pragma42.osgi.ferraris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;

public class GpioReceiveComponent implements EventHandler{

	public String httpGet(String urlString)
	{
		StringBuffer ret = new StringBuffer();
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
//                ret.append(line).append("\n");
//            	System.out.println(line);
                
            }
//            System.out.println(ret);
            reader.close();

        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
        return ret.toString();
	}
	
	public void forward2Erlang(long time){
		String url=String.format("http://vs0232.your-vserver.de:8082/erl/aclog:logdata?time=%d&turns=1",time);
		httpGet(url);
	}

	@Override
	public void handleEvent(Event event) {
		long timestamp = (Long) event.getProperty("time");
		LogWriterComponent.log(LogService.LOG_INFO, String.format("timestamp=%010d",timestamp));
		System.out.println(String.format("timestamp=%010d",timestamp));
		forward2Erlang(timestamp);
	}
}
