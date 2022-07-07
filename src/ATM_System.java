import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ATM_System {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random ra = new Random();
        ArrayList<Account> user_array = new ArrayList<>();      //�洢�û���Ϣ�ļ��ϣ�ÿ��Ԫ����һ���û���Ϣ����
        ArrayList<String> userNumber_array = new ArrayList<>(); //�洢�Ѿ����ڵĿ���

        //������
        while (true) {
            System.out.println("==========��ӭʹ��ATMϵͳ==========");
            System.out.println(">> 1����¼");
            System.out.println(">> 2��ע��");
            System.out.println(">> 3���˳�");

            int main_menu_command = sc.nextInt();
            String userNumber = create_userNumber(userNumber_array);
            switch (main_menu_command) {
                case 1 -> login(sc, user_array);
                case 2 -> signup(sc, ra, userNumber, user_array);
                case 3 -> System.exit(0);
                default -> System.out.println("�����������������!");
            }
        }
    }

    //��¼ģ��
    private static void login(Scanner sc, ArrayList<Account> userArray) {
        while (true) {
            System.out.println("==========��ӭʹ��ATMϵͳ==========");
            System.out.println("�����뿨�ţ�");
            String userNumber = sc.next();
            System.out.println("���������룺");
            String userPassword = sc.next();
            int whetherExist = 0;
            int index = 0;
            for (int  i = 0; i < userArray.size(); i++) {
                if (userNumber.equals(userArray.get(i).getUserNumber())) {
                    whetherExist = 1;
                    index = i;
                    break;
                }
            }
            if (whetherExist == 1) {
                if (userPassword.equals(userArray.get(index).getUserPassword())) {
                    int whetherStop = 0;
                    while (true) {
                        System.out.println("==========��ӭʹ��ATMϵͳ==========");
                        System.out.println(">> 1����ѯ");
                        System.out.println(">> 2�����");
                        System.out.println(">> 3��ȡ��");
                        System.out.println(">> 4��ת��");
                        System.out.println(">> 5���˳�");

                        int command_1 = sc.nextInt();
                        switch (command_1) {
                            case 1 :
                                query(sc, userArray, index);
                                break;
                            case 2 :
                                deposit(sc, userArray, index);
                                break;
                            case 3 :
                                withdrawal(sc, userArray, index);
                                break;
                            case 4 :
                                transfer(sc, userArray, index);
                                break;
                            case 5 :
                                whetherStop = 1;
                                break;
                            default : System.out.println("����������������ԣ�");
                        }
                        if (whetherStop == 1)
                            break;
                    }
                    break;
                }
                else {
                    System.out.println("������������ԣ�");
                }
            }
            else {
                System.out.println("������Ŀ��Ų����ڣ����������!");
            }
        }
    }

    private static void transfer(Scanner sc, ArrayList<Account> user_array, int index) {
        while (true) {
            System.out.println("==========��ӭʹ��ATMϵͳ==========");
            System.out.println("������Ҫת�˵��Ŀ��ţ�");
            String to_userNumber = sc.next();
            int to_index = 0;
            int whetherExist = 0;
            for (int i = 0; i < user_array.size(); i++) {
                if (to_userNumber.equals(user_array.get(i).getUserNumber())) {
                    to_index = i;
                    whetherExist = 1;
                    break;
                }
            }
            if (whetherExist == 1) {
                String to_name = user_array.get(to_index).getUserName().replace(user_array.get(to_index).getUserName().substring(0, 1), "*");
                System.out.println("��ȷ��Ҫ��" + to_name + "ת���𣿣�y/n��");
                String command_2 = sc.next();
                if (command_2.equals("y")) {
                    while (true) {
                        System.out.println("�������Ϊ��" + user_array.get(index).getBalance());
                        System.out.println("������Ҫת�˵Ľ�");
                        double transfer_amount = sc.nextDouble();
                        if (transfer_amount >= 0) {
                            if (transfer_amount <= user_array.get(index).getBalance()) {
                                user_array.get(index).setBalance(user_array.get(index).getBalance() - transfer_amount);
                                user_array.get(to_index).setBalance(user_array.get(to_index).getBalance() + transfer_amount);
                                System.out.println("ת�˳ɹ���");
                                break;
                            }
                            else {
                                System.out.println("���㣡");
                            }
                        }
                        else {
                            System.out.println("�������������ԣ�");
                        }
                    }
                }
                break;
            }
            else {
                System.out.println("������Ŀ��Ų����ڣ���������ԣ�");
            }
        }
    }

    private static void withdrawal(Scanner sc, ArrayList<Account> user_array, int index) {
        while (true) {
            System.out.println("==========��ӭʹ��ATMϵͳ==========");
            System.out.println("������Ҫȡ���Ľ�");
            double balance = user_array.get(index).getBalance();
            double withdrawal_amount = sc.nextDouble();
            if (withdrawal_amount >= 0) {
                if (withdrawal_amount <= user_array.get(index).getBalance()) {
                    if (withdrawal_amount <= user_array.get(index).getLimit()) {
                        balance -= withdrawal_amount;
                        user_array.get(index).setBalance(balance);
                        System.out.println("ȡ��ɹ����������Ϊ��" + user_array.get(index).getBalance());
                        break;
                    }
                    else {
                        System.out.println("ȡ��������ȡ���ȣ������ԣ�");
                    }
                }
                else {
                    System.out.println("���㣡");
                }
            }
            else {
                System.out.println("����������������ԣ�");
            }
        }
    }

    private static void deposit(Scanner sc, ArrayList<Account> user_array, int index) {
        while (true) {
            System.out.println("==========��ӭʹ��ATMϵͳ==========");
            System.out.println("������Ҫ����Ľ�");
            double balance = user_array.get(index).getBalance();
            double deposit_amount = sc.nextDouble();
            if (deposit_amount >= 0) {
                balance += deposit_amount;
                user_array.get(index).setBalance(balance);
                System.out.println("���ɹ����������Ϊ��" + user_array.get(index).getBalance());
                break;
            }
            else {
                System.out.println("�������������ԣ�");
            }
        }
    }

    private static void query(Scanner sc, ArrayList<Account> user_array, int index) {
        System.out.println("==========�û���Ϣ��ѯ����==========");
        System.out.println("�û�������" + user_array.get(index).getUserName());
        System.out.println("�û����ţ�" + user_array.get(index).getUserNumber());
        System.out.println("�˻���" +user_array.get(index).getBalance());
        System.out.println("����ȡ���޶" + user_array.get(index).getLimit());
    }

    //ע��ģ��
    private static void signup(Scanner sc, Random ra, String userNumber, ArrayList<Account> user_array) {
        Account user = new Account();
        while (true) {
            System.out.println("==========��ӭʹ��ATMע��ϵͳ==========");
            System.out.println("��������������:");
            String userName = sc.next();
            System.out.println("�����뵥��ȡ���޶�:");
            int limit = sc.nextInt();
            System.out.println("����������:");
            String password_1 = sc.next();
            System.out.println("��ȷ������:");
            String password_2 = sc.next();

            //�Ƚ���������������Ƿ�һ��
            if (password_1.equals(password_2)) {
                user.setUserName(userName);
                user.setLimit(limit);
                user.setUserPassword(password_1);
                user.setUserNumber(userNumber);
                user_array.add(user);
                int index = 0;
                for (int i = 0 ; i < user_array.size(); i++)
                    index = i;
                System.out.println("ע��ɹ�!");
                System.out.println("�û�������" + user_array.get(index).getUserName());
                System.out.println("�û����ţ�" + user_array.get(index).getUserNumber());
                System.out.println("�˻���" + user_array.get(index).getBalance());
                System.out.println("����ȡ���޶" + user_array.get(index).getLimit());
                break;
            }
            else {
                System.out.println("�������벻һ�£������ԣ�");
            }
        }
    }

    //��������ģ��
    private static String create_userNumber(ArrayList<String> userNumber_array) {
        String userNumber = "";
        Random ra = new Random();
        for (int i = 0; i < 10; i++) {
            userNumber += ra.nextInt(10);
        }
        while (userNumber_array.contains(userNumber)) {
            userNumber = "";
            for (int i = 0; i < 10; i++) {
                userNumber += ra.nextInt(10);
            }
        }
        userNumber_array.add(userNumber);
        return userNumber;
    }
}
