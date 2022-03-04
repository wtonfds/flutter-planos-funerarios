package br.com.monitoratec.farol.utils.responses;

import br.com.monitoratec.farol.graphql.exceptions.MappedFieldErrorValidationToCommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;

public class StatusCodes {
    public static class Success {

        public static class Login {
            public static final CommonResponse LOGGED_IN = new CommonResponse(1L, "Logged successfully", false);
            public static final CommonResponse LOGGED_OUT = new CommonResponse(2L, "Logged out successfully", false);
        }

        public static class User {
            public static final CommonResponse GOT_USER = new CommonResponse(1001L, "Got current user successfully", false);
            public static final CommonResponse REGISTERED_NEW_CUSTOMER = new CommonResponse(1002L, "Registered new user successfully", false);
            public static final CommonResponse FOUND_FAROL_USERS = new CommonResponse(1004L, "Found users successfully", false);
            public static final CommonResponse UPDATED_USER_SET_DEAD = new CommonResponse(1005L, "Updated user set dead", false);
            public static final CommonResponse PASSWORD_RESET = new CommonResponse(1006L, "Password reset successfully", false);
            public static final CommonResponse GOT_HOLDER = new CommonResponse(1007L, "Got holder user successfully", false);
            public static final CommonResponse TEMPORARY_PASSWORD_RESET = new CommonResponse(1008L, "Temporary password reset successfully", false);
            public static final CommonResponse REGISTERED_DEPENDENTS = new CommonResponse(1009L, "Dependents saved successfully", false);
            public static final CommonResponse GOT_CLIENT_SUCCESS = new CommonResponse(1010L, "Client found successfully", false);
            public static final CommonResponse UPDATED_DEPENDENT = new CommonResponse(1011L, "Dependent updated successfully", false);
            public static final CommonResponse GRACE_PERIOD_RETURNED_SUCCESSFULLY = new CommonResponse(1012L, "Check user grace period successfully", false);
            public static final CommonResponse UPDATED_USER_SUCCESSFULLY = new CommonResponse(1013L, "Updated user successfully", false);
            public static final CommonResponse USER_DISABLED_SUCCESSFULLY = new CommonResponse(1014L, "User disabled successfully", false);
            public static final CommonResponse DISCHARGE_STATEMENT_SUCCESSFULLY_SENT = new CommonResponse(1015L, "Discharge statement successfully sent", false);
            public static final CommonResponse SMS_SENT = new CommonResponse(1016L, "SMS sent", false);
            public static final CommonResponse SMS_CONFIRMED = new CommonResponse(1017L, "SMS code confirmed", false);
            public static final CommonResponse SAVED_TRIAL_USER_SUCCESSFULLY = new CommonResponse(1018L, "Trial user saved successfully", false);
            public static final CommonResponse UPDATED_ONE_SIGNAL_PREFERENCES_SUCCESSFULLY = new CommonResponse(1019L, "Updated one signal preferences successfully", false);
            public static final CommonResponse GOT_ONE_SIGNAL_PREFERENCES_SUCCESSFULLY = new CommonResponse(1019L, "Got one signal preferences successfully", false);
            public static final CommonResponse USER_GRACE_DAYS_REMOVED = new CommonResponse(1020L, "User grace days removed", false);
            public static final CommonResponse UPDATED_HOLDER_AND_DEPENDENT_INFORMATION_SUCCESSFULLY = new CommonResponse(1021L, "Updated holder and dependent information with documents successfully", false);
        }

        public static class Accredited {
            public static final CommonResponse REGISTERED_NEW_ACCREDITED = new CommonResponse(2001L, "Registered new accredited successfully", false);
            public static final CommonResponse FOUND_ACCREDITED_USERS = new CommonResponse(2002L, "Found users successfully", false);
            public static final CommonResponse GOT_ACCREDITED = new CommonResponse(2003L, "Got current accredited successfully", false);
        }

        public static class GeneralParameterization {
            public static final CommonResponse GOT_GENERAL_PARAMETERIZATION = new CommonResponse(3001L, "Got general parameterization successfully", false);
            public static final CommonResponse GOT_PLAN_BENEFITS_SUCCESSFULLY = new CommonResponse(3002L, "Got plan benefits successfully", false);
        }

        public static class Products {
            public static final CommonResponse REGISTERED_NEW_PRODUCT = new CommonResponse(4001L, "Registered new product successfully", false);
            public static final CommonResponse FOUND_PRODUCTS = new CommonResponse(4002L, "Found products successfully", false);
        }

        public static class Plan {
            public static final CommonResponse REGISTERED_NEW_PLAN = new CommonResponse(5001L, "Registered new plan successfully", false);
            public static final CommonResponse GOT_PLAN = new CommonResponse(5002L, "Got plan successfully", false);
            public static final CommonResponse FOUND_PLANS = new CommonResponse(5003L, "Found plans successfully", false);
            public static final CommonResponse SUBSCRIBED_TO_PLAN = new CommonResponse(5004L, "Subscribed to a plan successfully", false);
            public static final CommonResponse CONTRACT_REACTIVATED = new CommonResponse(5005L, "Contract is reactivated", false);
            public static final CommonResponse UPDATED_ADDRESS = new CommonResponse(5006L, "Address updated successfully", false);
            public static final CommonResponse DELETED_DEPENDENTS_SUCCESSFULLY = new CommonResponse(5007L, "Removed dependents successfully", false);
            public static final CommonResponse GOT_SUBSCRIBED_PLAN = new CommonResponse(5008L, "Subscribed plan found successfully", false);
            public static final CommonResponse CONTRACT_URL_RETRIEVED = new CommonResponse(5009L, "Contract generated successfully", false);
            public static final CommonResponse UNSUBSCRIBED = new CommonResponse(5010L, "Unsubscribed successfully", false);
            public static final CommonResponse CHARGE_EMAIL_SENT_SUCCESSFULLY = new CommonResponse(5011L, "Email sent to client successfully", false);
            public static final CommonResponse FOUND_DEPENDENTS_SUCCESSFULLY = new CommonResponse(5012L, "Found dependents successfully", false);
            public static final CommonResponse PAYMENT_SLIP_URL_RETRIEVED = new CommonResponse(5013L, "Payment slip url retrieved successfully", false);
            public static final CommonResponse PAYMENT_DAYS_RETRIEVED = new CommonResponse(5014L, "Payment days retrieved successfully", false);
            public static final CommonResponse ANTICIPATION_MONTHS_AVAILABLE_RETRIEVED_SUCCESSFULLY = new CommonResponse(5015L, "Anticipation days available retrieved successfully", false);
            public static final CommonResponse PAYMENT_MONTHS_RETRIEVED = new CommonResponse(5016L, "Payment Months retrieved successfully", false);
        }

        public static class PriceTable {
            public static final CommonResponse REGISTERED_NEW_PRICE_TABLE = new CommonResponse(6001L, "Registered new price table successfully", false);
            public static final CommonResponse UPDATED_PRICE_TABLE = new CommonResponse(6002L, "Updated the price table successfully", false);
            public static final CommonResponse FOUND_PRICE_TABLES = new CommonResponse(6003L, "Found price tables successfully", false);
            public static final CommonResponse GOT_PRICE_TABLE = new CommonResponse(6004L, "Got price table successfully", false);
        }

        public static class LotteryNumbers {
            public static final CommonResponse GOT_LOTTERYNUMBERS = new CommonResponse(7001L, "Got lottery numbers successfully", false);
            public static final CommonResponse GENERATED_LOTTERYNUMBERS = new CommonResponse(7002L, "Generated lottery numbers successfully", false);
        }

        public static class Campaign {
            public static final CommonResponse REGISTERED_NEW_CAMPAIGN = new CommonResponse(8001L, "Registered new campaign successfully", false);
            public static final CommonResponse UPDATED_CAMPAIGN = new CommonResponse(8002L, "Updated the campaign successfully", false);
            public static final CommonResponse FOUND_CAMPAIGNS = new CommonResponse(8003L, "Found campaigns successfully", false);
            public static final CommonResponse GOT_CAMPAIGN = new CommonResponse(8004L, "Got campaign successfully", false);
        }

        public static class MaterializedViews {
            public static final CommonResponse UPDATED_MATERIALIZED_VIEWS = new CommonResponse(9001L, "Updated all materialized views successfully", false);
        }

        public static class Lottery {
            public static final CommonResponse APPROVED_LOTTERY_NUMBERS_SUCCESSFULLY = new CommonResponse(10001L, "Lottery numbers approved successfully", false);
        }

        public static class Push {
            public static final CommonResponse PUSH_SENT_SUCCESSFULLY = new CommonResponse(11001L, "Push sent successfully", false);
        }

        public static class Payment {
            public static final CommonResponse AUTHORIZATION_SUCCESSFUL = new CommonResponse(12001L, "Got authorization token successfully", false);
            public static final CommonResponse FOUND_PAYMENT_SLIP = new CommonResponse(12002L, "Found Payment slip successfully", false);
            public static final CommonResponse REGISTERED_NEW_PAYMENT_SLIP = new CommonResponse(12003L, "Registered new payment slip successfully", false);
            public static final CommonResponse CREDIT_CARD_UPDATED = new CommonResponse(12004L, "Credit card updated", false);
            public static final CommonResponse CHANGED_PAYMENT_METHOD_SUCCESSFULLY = new CommonResponse(12005L, "Changed payment method successfully", false);
            public static final CommonResponse FOUND_PAYMENT_SITUATION = new CommonResponse(12006L, "Found payment situation successfully", false);
            public static final CommonResponse DISCOUNTS_ADDED_SUCCESSFULLY = new CommonResponse(12007L, "Discounts saved successfully", false);
            public static final CommonResponse DISCOUNTS_RETRIEVED_SUCCESSFULLY = new CommonResponse(12008L, "Discounts retrieved successfully", false);
            public static final CommonResponse ANTICIPATE_PAYMENT_SUCCESSFULLY = new CommonResponse(12009L, "Anticipated payment successfully", false);
            public static final CommonResponse CREATED_PAYMENT_MONTH_SUCCESSFULLY = new CommonResponse(12010L, "Created payment month successfully", false);
        }

        public static class Tem {
            public static final CommonResponse GOT_CARD_SUCCESSFULLY = new CommonResponse(14001L, "Got Tem card successfully", false);
            public static final CommonResponse USER_CANCELLED_ON_TEM_SUCCESSFULLY = new CommonResponse(14002L, "User cancelled on Tem successfully", false);
        }

        public static class Company {
            public static final CommonResponse SAVED_COMPANY_INFO_SUCCESSFULLY = new CommonResponse(15001L, "Saved company information successfully", false);
        }

        public static class Category {
            public static final CommonResponse GOT_CATEGORIES_SUCCESSFULLY = new CommonResponse(16001L, "Got all categories successfully", false);
        }
    }

    public static class Error {
        public static class Login {
            public static final CommonResponse SOMETHING_UNEXPECTED_HAPPENED = new CommonResponse(-1L, "Something unexpected happened", true);
            public static final CommonResponse REQUEST_TIMED_OUT = new CommonResponse(-2L, "Request timed out", true);
            public static final CommonResponse AUTHORIZATION_MISSING = new CommonResponse(-3L, "Authorization token couldn't be found", true);
            public static final CommonResponse AUTHORIZATION_INVALID = new CommonResponse(-4L, "Authorization token is not recognized", true);
            public static final CommonResponse NON_AUTHORIZED = new CommonResponse(-5L, "Non authorized to do this", true);
            public static final CommonResponse LOGIN_INFO_INVALID = new CommonResponse(-7L, "Login info is invalid", true);
            public static final CommonResponse CLIENT_IS_DEFAULT = new CommonResponse(-8L, "Client is default", true);
        }

        public static class User {
            public static final CommonResponse USER_NOT_FOUND = new CommonResponse(-1001L, "User was not found", true);
            public static final CommonResponse EMAIL_ALREADY_USED = new CommonResponse(-1002L, "Email is already in use", true);
            public static final CommonResponse PASSWORD_DONT_MATCH = new CommonResponse(-1005L, "Password don't match", true);
            public static final CommonResponse INVALID_CPF = new CommonResponse(-1006L, "Invalid CPF", true);
            public static final CommonResponse USER_IS_ALREADY_DEAD = new CommonResponse(-1007L, "User is already dead", true);
            public static final CommonResponse PASSWORD_REQUIRED_FIELD = new CommonResponse(-1008L, "Password is a required field", true);
            public static final CommonResponse EMAIL_OR_BIRTHDAY_DONT_MATCH = new CommonResponse(-1009L, "Email or birthday don't match", true);
            public static final CommonResponse METHOD_NOT_YET_IMPLEMENTED = new CommonResponse(-1010L, "Method is not yet implemented", true);
            public static final CommonResponse IMPOSSIBLE_TO_RECOVER_PASSWORD = new CommonResponse(-1011L, "Impossible to recover password through this option", true);
            public static final CommonResponse DEPENDENT_SHOULD_NOT_BE_ACTIVE = new CommonResponse(-1012L, "Dependent should not be active to be updated", true);
            public static final CommonResponse USER_SHOULD_BE_GUEST = new CommonResponse(-1013L, "User should be guest", true);
            public static final CommonResponse USER_IS_NOT_DEPENDENT = new CommonResponse(-1014L, "User can't upgrade, because he's not a dependent", true);
            public static final CommonResponse ERROR_FETCHING_PERMISSIONS = new CommonResponse(-1015L, "Error fetching user permissions", true);
            public static final CommonResponse INVALID_EMAIL = new CommonResponse(-1016L, "User with an invalid email", true);
            public static final CommonResponse USER_SHOULD_NOT_HAVE_HOLDER = new CommonResponse(-1017L, "User should not have holder", true);
            public static final CommonResponse ERROR_UPLOADING_DISCHARGE_STATEMENT = new CommonResponse(-1018L, "Could not upload discharge statement to S3", true);
            public static final CommonResponse INVALID_SMS_CODE = new CommonResponse(-1019L, "Sms code is invalid", true);
            public static final CommonResponse SMS_CODE_NOT_REGISTERED = new CommonResponse(-1020L, "There is no sms code registered for this user", true);
            public static final CommonResponse AUTH_TOKEN_NOT_VALID = new CommonResponse(-1021L, "Auth token is not valid", true);
            public static final CommonResponse USER_DOES_NOT_HAVE_AUTH_TOKEN = new CommonResponse(-1022L, "User doesn't have auth token", true);
            public static final CommonResponse CHILD_AGE_INVALID = new CommonResponse(-1023L, "Child age should not be equals or above 24 years", true);
            public static final CommonResponse USER_HAVE_NOT_PAID = new CommonResponse(-1024L, "User have not paid all the months in the required range", true);
            public static final CommonResponse DEPENDENT_DONT_HAVE_CORRECT_HOLDER = new CommonResponse(-1025L, "Dependent dont have the correct holder id, impossible to update", true);
            public static final CommonResponse DEPENDENT_SHOULD_BE_GUEST = new CommonResponse(-1026L, "Current dependent should have ClientType equals GUEST", true);
            public static final CommonResponse CPF_ALREADY_IN_USE = new CommonResponse(-1027L, "CPF already in use", true);
        }

        public static class Inputs {
            public static final CommonResponse UNMAPPED_TYPE = new CommonResponse(-2001L, "Error when validating a unmapped field. Please ask the devs to map it :-).", true);
            public static final CommonResponse BAD_EMAIL = new CommonResponse(-2002L, "Error when validating a Email field.", true);
            public static final CommonResponse BAD_DATE = new CommonResponse(-2003L, "Error when validating a Date field.", true);
            public static final CommonResponse BAD_DATE_TIME = new CommonResponse(-2004L, "Error when validating a DateTime field.", true);
            public static final CommonResponse BAD_TIME = new CommonResponse(-2005L, "Error when validating a Time field.", true);
            public static final CommonResponse BAD_SEX_ENUM = new CommonResponse(-2006L, "Error when validating a SexEnum field.", true);
        }

        public static class Accredited {
            public static final CommonResponse EMAIL_ALREADY_USED = new CommonResponse(-3001L, "Email is already in use", true);
            public static final CommonResponse CNPJ_ALREADY_USED = new CommonResponse(-3002L, "CNPJ is already in use", true);
            public static final CommonResponse ACCREDITED_NOT_FOUND = new CommonResponse(-3003L, "Accredited was not found", true);
        }

        public static class Product {
            public static final CommonResponse ACCREDITED_NOT_FOUND = new CommonResponse(-4001L, "Accredited not found", true);
            public static final CommonResponse FAROL_USER_NOT_FOUND = new CommonResponse(-4002L, "Farol user not found", true);
        }

        public static class Plan {
            public static final CommonResponse PLAN_NOT_FOUND = new CommonResponse(-5001L, "Plan was not found", true);
            public static final CommonResponse INVALID_GRACE_DAYS_PERIOD = new CommonResponse(-5002L, "Invalid grace days period", true);
            public static final CommonResponse USER_IS_DEAD = new CommonResponse(-5003L, "User is dead", true);
            public static final CommonResponse USER_IS_ALREADY_SUBSCRIBED = new CommonResponse(-5004L, "User is already subscribed to a plan", true);
            public static final CommonResponse SUBSCRIBED_PLAN_NOT_FOUND = new CommonResponse(-5005L, "The subscribed plan was not found", true);
            public static final CommonResponse SUBSCRIPTION_ALREADY_ACTIVE = new CommonResponse(-5006L, "Contract is already active", true);
            public static final CommonResponse USER_ALREADY_HAVE_ACTIVE_CONTRACT = new CommonResponse(-5007L, "User already have an active contract", true);
            public static final CommonResponse COMMENTARY_IS_EMPTY = new CommonResponse(-5008L, "The reactivation commentary can't be null or empty", true);
            public static final CommonResponse INVALID_SUBSCRIBED_PLAN = new CommonResponse(-5009L, "Invalid subscribed plan or user don't have one active", true);
            public static final CommonResponse COULD_NOT_DOWNLOAD_CONTRACT = new CommonResponse(-5010L, "Could not download the contract, try again later", true);
            public static final CommonResponse ERROR_UPLOADING_CONTRACT = new CommonResponse(-5011L, "Could not upload contract to S3", true);
            public static final CommonResponse CANNOT_CANCEL_PLAN = new CommonResponse(-5012L, "Could not cancel the plan with more than 30 days without payment", true);
            public static final CommonResponse INVALID_PARCEL_QUANTITY = new CommonResponse(-5013L, "Parcel number should be between 2 and 12", true);
            public static final CommonResponse CURRENT_PLAN_NOT_PAID = new CommonResponse(-5014L, "The current subscribed plan is not paid, impossible to anticipate parcel", true);
            public static final CommonResponse CANNOT_CHANGE_PAYMENT_METHOD = new CommonResponse(-5015L, "Could not change payment method. Payment is anticipated and have a new dependent", true);
            public static final CommonResponse INVALID_NUMBER_OF_PARCELS = new CommonResponse(-5016L, "Number of parcels superior to the limit", true);
            public static final CommonResponse INVALID_PAYMENT_METHOD = new CommonResponse(-5017L, "The selected payment method is invalid", true);
        }

        public static class PriceTable {
            public static final CommonResponse EMPTY_AGE_RANGE = new CommonResponse(-6001L, "No age range defined", true);
            public static final CommonResponse INVALID_AGE_RANGE = new CommonResponse(-6002L, "Invalid age range (negative value or end > start)", true);
            public static final CommonResponse OVERLAPPING_AGE = new CommonResponse(-6003L, "Some age ranges are overlapping", true);
            public static final CommonResponse AGE_NOT_COVERED = new CommonResponse(-6004L, "Some ages are not covered by the current ranges", true);
            public static final CommonResponse PLAN_PRICE_TABLE_NOT_FOUND = new CommonResponse(-6005L, "The requested price table was not found", true);
            public static final CommonResponse UPGRADE_PRICE_TABLE_NOT_FOUND = new CommonResponse(-6006L, "The requested price table was not found", true);
            public static final CommonResponse DEPENDENT_PRICE_TABLE_NOT_FOUND = new CommonResponse(-6007L, "The requested price table was not found", true);
        }

        public static class Campaign {
            public static final CommonResponse CAMPAIGN_NOT_FOUND = new CommonResponse(-9001L, "The requested campaign was not found", true);
        }

        public static class Lottery {
            public static final CommonResponse LOTTERY_NUMBERS_NOT_FOUND = new CommonResponse(-10001L, "The requested lottery number was not found", true);
            public static final CommonResponse LOTTERY_NUMBERS_ALREADY_APPROVED = new CommonResponse(-10002L, "Lottery numbers already approved", true);
        }

        public static class Push {
            public static final CommonResponse USERS_NOT_FOUND = new CommonResponse(-11001L, "Users was not found", true);
            public static final CommonResponse MESSAGE_IS_EMPTY = new CommonResponse(-11002L, "Message is empty", true);
            public static final CommonResponse ERROR_SENDING_PUSH = new CommonResponse(-11003L, "Error sending push", true);
        }

        public static class Payment {
            public static final CommonResponse NO_CARD_FOUND_OR_SAVED = new CommonResponse(-12001L, "No card saved and no card in request", true);
            public static final CommonResponse NO_PENDING_PAYMENT_SLIP = new CommonResponse(-12002L, "No pending payment slip found", true);
            public static final CommonResponse FAILED_CHANGING_PAYMENT_METHOD = new CommonResponse(-12003L, "Failed changing payment method", true);
            public static final CommonResponse PAYMENT_METHOD_IS_NOT_DEBIT_CARD = new CommonResponse(-12004L, "Actual payment method is not debit card", true);
            public static final CommonResponse PAYMENT_METHOD_IS_NOT_PAYMENT_SLIP = new CommonResponse(-12005L, "Actual payment method is not payment slip", true);
            public static final CommonResponse PAYMENT_METHOD_IS_NOT_CREDIT_CARD = new CommonResponse(-12006L, "Actual payment method is not credit card", true);
            public static final CommonResponse INVALID_PAYMENT_DAY = new CommonResponse(-12007L, "Invalid payment day", true);
            public static final CommonResponse PAYMENT_SITUATION_NOT_FOUND = new CommonResponse(-12008L, "Client and/or subscribed plan not found", true);
            public static final CommonResponse NULL_RESPONSE_FROM_GETNET = new CommonResponse(-12009L, "Fatal error, response from getnet was null", true);
            public static final CommonResponse FAILED_TO_CANCEL_CLIENT_GETNET = new CommonResponse(-12011L, "Failed to cancel client on getnet", true);

            public static CommonResponse PAYMENT_FAILED_ON_GETNET(String description) {
                return new CommonResponse(-12010L, description, true);
            }
        }

        public static class AmazonS3 {
            public static final CommonResponse UNABLE_TO_SEND_IMAGE = new CommonResponse(-13001L, "Unable to send image", true);
            public static final CommonResponse UNABLE_TO_SEND_XML = new CommonResponse(-13002L, "Unable to send xml file", true);
            public static final CommonResponse UNABLE_TO_GET_BYTES = new CommonResponse(-13003L, "Unable to get bytes from specific file", true);
        }

        public static class Tem {
            public static final CommonResponse TEM_COULD_NOT_SUBSCRIBE = new CommonResponse(-14001L, "Could not subscribe on TEM", true);
            public static final CommonResponse TEM_COULD_NOT_GET_TOKEN = new CommonResponse(-14002L, "Could not get TEM authentication token", true);
            public static final CommonResponse TEM_CARD_NOT_FOUND = new CommonResponse(-14003L, "Could not find TEM card", true);
            public static final CommonResponse TEM_COULD_NOT_UPDATE_STATUS = new CommonResponse(-14004L, "Could not update TEM status", true);
            public static final CommonResponse TEM_INTEGRATION_NOT_BEING_USED = new CommonResponse(-14005L, "TEM integration is not active", true);
        }
    }

    public static CommonResponse fromMappedFieldErrorValidation(MappedFieldErrorValidationToCommonResponse mappedFieldErrorValidationToCommonResponse) {
        switch (mappedFieldErrorValidationToCommonResponse) {
            case EMAIL:
                return StatusCodes.Error.Inputs.BAD_EMAIL.createCopy();
            case DATE:
                return StatusCodes.Error.Inputs.BAD_DATE.createCopy();
            case DATE_TIME:
                return Error.Inputs.BAD_DATE_TIME.createCopy();
            case TIME:
                return Error.Inputs.BAD_TIME.createCopy();
            case SEX_ENUM:
                return Error.Inputs.BAD_SEX_ENUM.createCopy();

            default:
                return Error.Inputs.UNMAPPED_TYPE.createCopy();
        }
    }
}
