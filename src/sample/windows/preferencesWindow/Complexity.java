package sample.windows.preferencesWindow;

import sample.Main;

public enum Complexity {
    LIGHT{
        public int getSalary(){
            return 600 + Main.random.nextInt(600);
        }
        public int getFirstEducationPrice(){
            return 500;
        }
        public int getSecondEducationPrice(){
            return 1500;
        }
    },
    MEDIUM{
        public int getSalary(){
            return  200 +Main.random.nextInt(500);
        }
        public int getFirstEducationPrice(){
            return 2000;
        }
        public int getSecondEducationPrice(){
            return 5000;
        }
    },
    HARD{
        public int getSalary(){
            return  Main.random.nextInt(100);
        }
        public int getFirstEducationPrice(){
            return 7000;
        }
        public int getSecondEducationPrice(){
            return 14000;
        }
    };
    public abstract int getSalary();
    public abstract int getFirstEducationPrice();
    public abstract int getSecondEducationPrice();
}
