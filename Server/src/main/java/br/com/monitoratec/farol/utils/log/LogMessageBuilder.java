package br.com.monitoratec.farol.utils.log;

public final class LogMessageBuilder {
    public static String buildSubscribePlanMessage(String clientCpf, String clientName, Long planId) {
        return "Usuário " + clientName + " com o CPF " + clientCpf + " registrou se ao plano " + planId;
    }

    public static String buildIncludedDependentMessage(String clientCpf, String clientName, String dependentName,
                                                       String dependentType) {
        return "Usuário " + clientName + " com o CPF " + clientCpf + " adicionou o dependente " + dependentName +
                " do tipo " + dependentType;
    }

    public static String buildRemovedDependentMessage(String clientCpf, String clientName, String dependentName,
                                                      String dependentType) {
        return "Usuário " + clientName + " com o CPF " + clientCpf + " removeu o dependente " + dependentName +
                " do tipo " + dependentType;
    }

    public static String buildUnsubscribedFromPlanMessage(String clientCpf, String clientName, Long planId) {
        return "Usuário " + clientName + " com o CPF " + clientCpf + " cancelou o plano " + planId;
    }

    public static String buildReactivatedContractMessage(String clientName, String clientCpf, String commentary, Long planId) {
        return "Usuário " + clientName + " com o CPF " + clientCpf + " teve o contrato reativado do plano " + planId +
                " pelo motivo " + commentary;
    }
}
