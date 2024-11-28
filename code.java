import java.util.Scanner;

class PlayerStatus {
    private String nickname;
    private int score;
    private int lives;
    private int health = 100;
    private String weaponInHand;
    private double positionX;
    private double positionY;
    private static String gameName;

    public  void initPlayer(String nickname) {
        this.nickname = nickname;
        this.lives = 3;
        this.health = 100;
        this.weaponInHand = "knife";
    }
    public String getNickname() {
        return nickname;
    }
    public void initPlayer(String nickname, int lives) {
        initPlayer(nickname);
        this.lives = lives;
    }

    public void initPlayer(String nickname, int lives, int score) {
        initPlayer(nickname, lives);
        this.score = score;
    }

    public static String getGameName() {
        return gameName;
    }

    public String getWeaponInHand() {
        return weaponInHand;
    }
    public static void setGameName(String newName) {
        gameName = newName;
    }

    public boolean setWeaponInHand(String weapon) {
        if (isValidWeapon(weapon) && canBuyWeapon(weapon)) {
            this.weaponInHand = weapon;
            if (weapon.equals("knife")) {
                this.score -= 1000;
            } else if (weapon.equals("sniper")) {
                this.score -= 10000;
            } else if (weapon.equals("kalashnikov")) {
                this.score -= 20000;
            }
            return true;
        }
        return false;
    }

    public void findArtifact(int artifactCode) {
        if (isPerfectNumber(artifactCode)) {
            this.score += 5000;
            this.lives++;
            this.health = 100;
        } else if (isPrimeNumber(artifactCode)) {
            this.score += 1000;
            this.lives += 2;
            this.health = Math.min(100, this.health + 25);
        } else if (artifactCode % 2 == 0 && digitSumDivisibleByThree(artifactCode)) {
            this.score -= 3000;
            this.health = Math.max(0, this.health - 25);
            if (this.health <= 0) {
                this.lives--;
                this.health = 100;
            }
        } else {
            this.score += artifactCode;
        }
    }

    public void movePlayerTo(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public boolean shouldAttackOpponent(PlayerStatus opponent) {
        int distance = calculateDistance(this.positionX, this.positionY, opponent.positionX, opponent.positionY);

        if (this.weaponInHand.equals(opponent.weaponInHand)) {
            int thisPlayerPower = (3 * this.health + this.score / 1000) / 4;
            int opponentPower = (3 * opponent.health + opponent.score / 1000) / 4;
            return thisPlayerPower > opponentPower;
        } else {
            if (distance > 1000) {
                return getWeaponPower(this.weaponInHand) > getWeaponPower(opponent.weaponInHand);
            } else {
                return getWeaponPower(this.weaponInHand) < getWeaponPower(opponent.weaponInHand);
            }
        }
    }

    private boolean isValidWeapon(String weapon) {
        return weapon.equals("knife") || weapon.equals("sniper") || weapon.equals("kalashnikov");
    }

    private boolean canBuyWeapon(String weapon) {
        int weaponPrice = getWeaponPrice(weapon);
        return this.score >= weaponPrice;
    }

    private int getWeaponPrice(String weapon) {
        if (weapon.equals("knife")) {
            return 1000;
        } else if (weapon.equals("sniper")) {
            return 10000;
        } else if (weapon.equals("kalashnikov")) {
            return 20000;
        }
        return 0;
    }

    private int getWeaponPower(String weapon) {
        if (weapon.equals("knife")) {
            return 1;
        } else if (weapon.equals("sniper")) {
            return 2;
        } else if (weapon.equals("kalashnikov")) {
            return 3;
        }
        return 0;
    }

    private int calculateDistance(double x1, double y1, double x2, double y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private boolean isPerfectNumber(int number) {

        return false;
    }

    private boolean isPrimeNumber(int number) {

        return false;
    }

    private boolean digitSumDivisibleByThree(int number) {

        return false;
    }

    public String toString() {
        return "PlayerStatus{" +
                "nickname='" + nickname + '\'' +
                ", score=" + score +
                ", lives=" + lives +
                ", health=" + health +
                ", weaponInHand='" + weaponInHand + '\'' +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCase = scanner.nextInt();
        PlayerStatus player1;
        PlayerStatus player2;
        PlayerStatus player3;

        switch (testCase) {
            case 0:
                // Sanity check
                System.out.println("Sanity check");
                break;
            case 1:
                // Check fields, initializers, getters, setters
                player1 = new PlayerStatus();
                player1.initPlayer("player1");
                player1.initPlayer("player1", 1, 999999);
                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1);
                player2.initPlayer("player2", 1, 9999999);
                player3 = new PlayerStatus();
                player3.initPlayer("player3", 2, 9999999);

                System.out.println(player1.getNickname());
                System.out.println(player2.getNickname());
                System.out.println(player3.getNickname());

                PlayerStatus.setGameName("World of DevMind");
                System.out.println(PlayerStatus.getGameName());
                PlayerStatus.setGameName("Call of DevMind");
                System.out.println(PlayerStatus.getGameName());

                player1.setWeaponInHand("knife");
                System.out.println(player1.getWeaponInHand());
                player1.setWeaponInHand("lightsaber");
                System.out.println(player1.getWeaponInHand());
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player2.getWeaponInHand());
                player3.setWeaponInHand("sniper");
                System.out.println(player3.getWeaponInHand());
                break;
            case 2:
                // Test findArtifact for traps and player death
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                break;
            case 3:
                // Test findArtifact for traps and gaining lives
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player1.findArtifact(28);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);
                player1.findArtifact(300);

                System.out.println("empty");
                break;
            case 4:
                // Test findArtifact for gaining score and spending it on weapons
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 0);

                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(5000);
                System.out.println(player1.setWeaponInHand("sniper"));
                System.out.println(player1.setWeaponInHand("sniper"));
                player1.findArtifact(19000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                player1.findArtifact(1000);
                System.out.println(player1.setWeaponInHand("kalashnikov"));
                System.out.println(player1.getWeaponInHand());
                break;
            case 5:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(100, 100);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 6:
                // Test shouldAttackOpponent for close distance different weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(800, 800);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("kalashnikov");
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("knife");
                System.out.println(player1.shouldAttackOpponent(player2));
                player2.setWeaponInHand("sniper");
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 7:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 9999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999999999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("knife");
                player2.setWeaponInHand("knife");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 8:
                // Test shouldAttackOpponent for same weapon duel
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 99999);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 9:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("kalashnikov");
                player2.setWeaponInHand("kalashnikov");

                System.out.println(player1.shouldAttackOpponent(player2));
                player1.findArtifact(300);
                player2.movePlayerTo(1000, 1000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
            case 10:
                // Test shouldAttackOpponent for same weapon duel with different health
                player1 = new PlayerStatus();
                player1.initPlayer("player1", 1, 999999);

                player2 = new PlayerStatus();
                player2.initPlayer("player2", 1, 999900);

                player1.movePlayerTo(0, 0);
                player2.movePlayerTo(10, 10);

                player1.setWeaponInHand("sniper");
                player2.setWeaponInHand("sniper");

                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(300);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(7);
                System.out.println(player1.shouldAttackOpponent(player2));

                player1.findArtifact(2000);
                System.out.println(player1.shouldAttackOpponent(player2));
                break;
        }
    }
}
