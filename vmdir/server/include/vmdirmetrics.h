/*
 * Copyright © 2018 VMware, Inc.  All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the ?~@~\License?~@~]); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an ?~@~\AS IS?~@~] BASIS, without
 * warranties or conditions of any kind, EITHER EXPRESS OR IMPLIED.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

#define IS_METRICS_LDAP_WRITE_OP(writeOp) \
            (writeOp == METRICS_LDAP_OP_ADD    || \
             writeOp == METRICS_LDAP_OP_MODIFY || \
             writeOp == METRICS_LDAP_OP_DELETE)

#define IS_METRICS_LDAP_EXT_WRITE_IMPACTING_OP(extOp) \
            (extOp == METRICS_LDAP_OP_TYPE_EXTERNAL || \
             extOp == METRICS_LDAP_OP_TYPE_REPL)

#define IS_METRICS_LDAP_SUCCESS_OP(successOp) \
            (successOp == METRICS_LDAP_SUCCESS)

typedef enum
{
    METRICS_LDAP_OP_IGNORE = -1,
    METRICS_LDAP_OP_ADD,
    METRICS_LDAP_OP_MODIFY,
    METRICS_LDAP_OP_DELETE,
    METRICS_LDAP_OP_SEARCH,
    METRICS_LDAP_OP_COUNT

} METRICS_LDAP_OPS;

typedef enum
{
    METRICS_LDAP_OP_TYPE_EXTERNAL,
    METRICS_LDAP_OP_TYPE_INTERNAL,
    METRICS_LDAP_OP_TYPE_REPL,
    METRICS_LDAP_OP_TYPE_COUNT

} METRICS_LDAP_OP_TYPES;

typedef enum
{
    METRICS_LDAP_SUCCESS,
    METRICS_LDAP_UNAVAILABLE,
    METRICS_LDAP_SERVER_DOWN,
    METRICS_LDAP_UNWILLING_TO_PERFORM,
    METRICS_LDAP_INVALID_DN_SYNTAX,
    METRICS_LDAP_NO_SUCH_ATTRIBUTE,
    METRICS_LDAP_INVALID_SYNTAX,
    METRICS_LDAP_UNDEFINED_TYPE,
    METRICS_LDAP_TYPE_OR_VALUE_EXISTS,
    METRICS_LDAP_OBJECT_CLASS_VIOLATION,
    METRICS_LDAP_ALREADY_EXISTS,
    METRICS_LDAP_CONSTRAINT_VIOLATION,
    METRICS_LDAP_NOT_ALLOWED_ON_NONLEAF,
    METRICS_LDAP_PROTOCOL_ERROR,
    METRICS_LDAP_INVALID_CREDENTIALS,
    METRICS_LDAP_INSUFFICIENT_ACCESS,
    METRICS_LDAP_AUTH_METHOD_NOT_SUPPORTED,
    METRICS_LDAP_SASL_BIND_IN_PROGRESS,
    METRICS_LDAP_TIMELIMIT_EXCEEDED,
    METRICS_LDAP_SIZELIMIT_EXCEEDED,
    METRICS_LDAP_NO_SUCH_OBJECT,
    METRICS_LDAP_BUSY,
    METRICS_LDAP_OTHER,
    METRICS_LDAP_ERROR_COUNT

} METRICS_LDAP_ERRORS;

typedef enum
{
    METRICS_RPC_OP_GENERATEPASSWORD,
    METRICS_RPC_OP_GETKEYTABRECBLOB,
    METRICS_RPC_OP_CREATEUSER,
    METRICS_RPC_OP_CREATEUSEREX,
    METRICS_RPC_OP_SETLOGLEVEL,
    METRICS_RPC_OP_SETLOGMASK,
    METRICS_RPC_OP_SUPERLOGQUERYSERVERDATA,
    METRICS_RPC_OP_SUPERLOGENABLE,
    METRICS_RPC_OP_SUPERLOGDISABLE,
    METRICS_RPC_OP_ISSUPERLOGENABLED,
    METRICS_RPC_OP_SUPERLOGFLUSH,
    METRICS_RPC_OP_SUPERLOGSETSIZE,
    METRICS_RPC_OP_SUPERLOGGETSIZE,
    METRICS_RPC_OP_SUPERLOGGETENTRIESLDAPOPERATION,
    METRICS_RPC_OP_OPENDATABASEFILE,
    METRICS_RPC_OP_READDATABASEFILE,
    METRICS_RPC_OP_CLOSEDATABASEFILE,
    METRICS_RPC_OP_SETBACKENDSTATE,
    METRICS_RPC_OP_BACKUPDB,
    METRICS_RPC_OP_GETSTATE,
    METRICS_RPC_OP_GETLOGLEVEL,
    METRICS_RPC_OP_GETLOGMASK,
    METRICS_RPC_OP_COUNT

} METRICS_RPC_OPS;

typedef enum
{
    METRICS_SRV_DBSIZE,
    METRICS_SRV_BACKUP_TIMETAKEN,
    METRICS_SRV_STAT_COUNT

} METRICS_SRV_STAT;

typedef enum
{
    METRICS_LAYER_PROTOCOL,
    METRICS_LAYER_MIDDLELAYER,
    METRICS_LAYER_BACKEND,
    METRICS_LAYER_COUNT

} METRICS_LAYERS;


// libmain.c
DWORD
VmDirMetricsInitialize(
    VOID
    );

VOID
VmDirMetricsShutdown(
    VOID
    );

// enums.c
PSTR
VmDirMetricsLdapOperationString(
    METRICS_LDAP_OPS    operation
    );

PSTR
VmDirMetricsLdapOpTypeString(
    METRICS_LDAP_OP_TYPES   opType
    );

PSTR
VmDirMetricsLdapErrorString(
    METRICS_LDAP_ERRORS error
    );

PSTR
VmDirMetricsSrvStatString(
    METRICS_SRV_STAT srvStat
    );

PSTR
VmDirMetricsRpcOperationString(
    METRICS_RPC_OPS operation
    );

METRICS_LDAP_OP_TYPES
VmDirMetricsMapLdapOpTypeToEnum(
    VDIR_OPERATION_TYPE opType
    );

METRICS_LDAP_ERRORS
VmDirMetricsMapLdapErrorToEnum(
    int error
    );
