/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 * Provides an Account Balance and Basic Withdrawal/Deposit Operations
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    Account account = new Account();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            double balance = account.getBalance();
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            out.println(balance);
        } catch (IOException ex) {
            System.err.println("Something went wrong: " + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String depositParameter = request.getParameter("deposit");
        double depositAmount = 0;
        if (depositParameter != null) {
            depositAmount = Double.parseDouble(depositParameter);
            account.deposit(depositAmount);
        }

        String withdrawParameter = request.getParameter("withdraw");
        double withdrawAmount = 0;
        if (withdrawParameter != null) {
            withdrawAmount = Double.parseDouble(withdrawParameter);
            account.withdraw(withdrawAmount);
        }

        String closeParameter = request.getParameter("close");
        if (closeParameter != null){
            account.close();
        }
        
    }
}
