package Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    public long m() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("m"));

    }
    public long n() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("n"));

    }
    public long initPlanMin() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("init_plan_min"));

    }
    public long initPlanSec() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("init_plan_sec"));

    }
    public long initBudget() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("init_budget"));

    }
    public long initCenterDep() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("init_center_dep"));

    }
    public long planRevMin() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("plan_rev_min"));

    }
    public long planRevSec() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("plan_rev_sec"));

    }
    public long revCost() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("rev_cost"));

    }
    public long maxDep() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("max_dep"));

    }
    public long interestPct() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.txt")) {
            // Load the configuration file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return Integer.parseInt(properties.getProperty("interest_pct"));

    }


}
